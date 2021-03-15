package RecipApp;

import java.util.ArrayList;

public class TheApp {

    private FileManager file = new FileManager();

    public ArrayList<Ingredient> ingredients;
    public ArrayList<Recip> recips;
    private Recip recip;
    private UiConsoleSc ui = new UiConsoleSc();

    TheApp() {
        ingredients = new ArrayList<>();
        recips = new ArrayList<>();
    }

    public void viewIngredients() {
        int x = 0;
        for (Ingredient c : ingredients) {
            x++;
            System.out.print(x + ". " + c.getName());
            System.out.println();
        }
        System.out.println("\n\n");
    }

    private void addIngredient() {

        System.out.println("new ing: ");
        String ne = ui.stringGetter();

        System.out.println("new mått: ");
        String mg = ui.stringGetter();

        System.out.println("new pris: ");
        int pr = ui.intGetter();

        Ingredient ing = (new Ingredient(ne, mg, pr));
        ingredients.add(ing);
    }

    private void addRecip() {
        System.out.println("What is the name of the new Recip?\n");
        recip = new Recip(ui.stringGetter());
        addIngredientsToRecip(recip);
        recip.makingWay();
        recips.add(recip);

    }

    public void addIngredientsToRecip(Recip recip) {
        System.out.println("ADD new ingredient!");
        String x = "";

        while (!(x.equalsIgnoreCase("n"))) {
            viewIngredients();
            int theIngredient = ui.intGetter() - 1;
            while (theIngredient >= ingredients.size() || theIngredient == -1) {
                ui.wronger();
                System.out.println("Please Try To Insert A Right Value");
                theIngredient = ui.intGetter();
            }
            recip.addIngredient(ingredients.get(theIngredient), getHowMuch(), getIngredientReson());
            System.out.println("ADD new ingredient? (for no n)");
            x = ui.stringGetter();
        }
    }

    private double getHowMuch() {
        System.out.println("How Much?");
        return ui.doubleGetter();
    }

    private String getIngredientReson() {
        System.out.println("What Reson?");
        return ui.stringGetter();
    }

    private void viewAllRecips() {
        int x = 1;
        for (Recip c : recips) {
            System.out.print(x + ". " + c.getName() + "\n");
            x++;
        }
    }

    private void addIngredientsFromFile() {
        ArrayList<ArrayList<String>> fromFileIng = file.ingredientReader();
        for (ArrayList<String> ings : fromFileIng) {
            Ingredient ing = (new Ingredient(ings.get(0), ings.get(1), Double.parseDouble(ings.get(2))));
            ingredients.add(ing);
        }
    }

    private void addRecipsFromFile() {
        ArrayList<String> recipz = file.recipReader();
        for (String recp : recipz) {
            String[] rec = recp.split(",");
            String name = rec[0];
            Recip recip = new Recip(name);
            String ingrediens = rec[1].replace("||", ",");
            String[] ingrediensFromRecip = ingrediens.split(",");
            for (String ing : ingrediensFromRecip) {
                String thisOne = ing.replace(":", ",");
                String[] theOtherOne = thisOne.split(",");
                String theIngredient = theOtherOne[0].replace(" ", "");
                for (Ingredient ingr : ingredients) {
                    if (theIngredient.equals(ingr.getName())) {

                        while (theOtherOne[2].startsWith(" ")) {
                            theOtherOne[2] = theOtherOne[2].substring(1, theOtherOne[2].length());
                        }

                        recip.addIngredient(ingr, Double.parseDouble(theOtherOne[1].replace(" ", "")), theOtherOne[2]);
                        break;
                    }
                }
            }
            recips.add(recip);
        }
    }

    private void removeIngredient() {
        ui.standard();
        viewIngredients();
        int x = ui.intGetter() - 1;
        while (x >= ingredients.size() || x == -1) {
            ui.wronger();
            System.out.println("Please Try To Insert A Right Value");
            x = ui.intGetter() - 1;
        }
        System.out.println("Are u sure? (y for yes)");
        String y = ui.stringGetter();
        if (y.equals("y") || y.equals("Y")) {
            ingredients.remove(x);
            System.out.println("the ingredient have been removed");
        } else {
            System.out.println("avbryter");
        }
    }

    private void opening() {
        addIngredientsFromFile();
        addRecipsFromFile();
    }

    private void closing() {
        file.ingredientWriter(ingredients);
        file.recipWriter(recips);
    }

    private void ingredientMenu() {
        int ingVal = 100;
        while (ingVal != 0) {
            ui.ingredient();
            ingVal = ui.intGetter();
            if (ingVal == 1) {
                // view ingredients
                ui.ingredientView();
                int choose2 = ui.intGetter();
                if (choose2 == 1) {
                    // all ingr
                    viewIngredients();

                } else if (choose2 == 2) {
                    // specieal one
                    ui.standard();
                    viewIngredients();
                    int ingredientIndex = ui.intGetter() - 1;
                    while (ingredientIndex >= ingredients.size() || ingredientIndex == -1) {
                        ui.wronger();
                        System.out.println("Please Try To Insert A Right Value");
                        ingredientIndex = ui.intGetter() - 1;
                    }
                    System.out.println("Name: " + ingredients.get(ingredientIndex).getName());
                    System.out.println("Price: " + ingredients.get(ingredientIndex).getPrice());
                    System.out.println("Unit: " + ingredients.get(ingredientIndex).getUnit());
                } else
                    ui.wronger();

            } else if (ingVal == 2) {
                // add one new
                addIngredient();

            } else if (ingVal == 3) {
                // edit ingredient
                ui.standard();
                viewIngredients();
                int ingIndex = ui.intGetter() - 1;
                while (ingIndex >= ingredients.size() || ingIndex == -1) {
                    ui.wronger();
                    System.out.println("Please Try To Insert A Right Value");
                    ingIndex = ui.intGetter() - 1;
                }

                int editVal = 100;
                while (editVal != 0) {
                    ui.ingredeientEdit();
                    editVal = ui.intGetter();
                    if (editVal == 1) {
                        // name
                        System.out.println("New name Of " + ingredients.get(ingIndex).getName());
                        ingredients.get(ingIndex).editName(ui.stringGetter());
                    } else if (editVal == 2) {
                        // price
                        System.out.println("Price Of " + ingredients.get(ingIndex).getName() + "are "
                                + ingredients.get(ingIndex).getPrice());
                        System.out.println("New Price");
                        ingredients.get(ingIndex).editPrice(ui.doubleGetter());
                    } else if (editVal == 3) {
                        // unit
                        System.out.println("Unit Of " + ingredients.get(ingIndex).getName() + "are "
                                + ingredients.get(ingIndex).getUnit());
                        System.out.println("New Unit");
                        ingredients.get(ingIndex).editUnit(ui.stringGetter());
                    } else {
                        ui.wronger();
                    }
                }
            } else if (ingVal == 4) {
                // remove ingredient
                removeIngredient();
            } else {
                ui.wronger();
            }
        }
    }

    private void recipMenu() {
        // recips

        int choose2 = 100;
        while (choose2 != 0) {
            ui.recip();
            choose2 = ui.intGetter();
            if (choose2 == 1) {
                // view recip
                ui.recipView();
                int viewVal = ui.intGetter();

                if (viewVal == 1) {
                    // all recips
                    viewAllRecips();
                } else if (viewVal == 2) {
                    // specieal recip view
                    ui.standard();
                    viewAllRecips();
                    int recipIndex = ui.intGetter() - 1;
                    while (recipIndex >= recips.size() || recipIndex == -1) {
                        ui.wronger();
                        System.out.println("Please Try To Insert A Right Value");
                        recipIndex = ui.intGetter() - 1;
                    }
                    System.out.println(recips.get(recipIndex).viewRecip());
                } else {
                    ui.wronger();
                }
            } else if (choose2 == 2) {
                // add recip
                addRecip();
            } else if (choose2 == 3) {
                // edit recip
                recipEditer();
            }
        }
    }

    private void recipEditer() {
        ui.standard();
        viewAllRecips();

        int recipIndex = ui.intGetter() - 1;
        while (recipIndex >= recips.size() || recipIndex == -1) {
            ui.wronger();
            System.out.println("Please Try To Insert A Right Value");
            recipIndex = ui.intGetter() - 1;
        }

        int editVal = 100;
        while (editVal != 0) {
            ui.recipEdit();
            editVal = ui.intGetter();
            if (editVal == 1) {
                // edit recip name
                recips.get(recipIndex).editName();
            } else if (editVal == 2) {
                recipIngredientEditor(recipIndex);
            } else if (editVal == 3) {
                // edit making way
                System.out.println(recips.get(recipIndex).viewRecip());
                recips.get(recipIndex).editMakingWay();
            } else {
                ui.wronger();
            }

        }
    }

    private void recipIngredientEditor(int recipIndex) {
        // recip ingredients
        int editIngVal = 100;
        while (editIngVal != 0) {
            ui.recipEditIngredient();
            editIngVal = ui.intGetter();
            if (editIngVal == 1) {
                // add ingredient to recip
                addIngredientsToRecip(recips.get(recipIndex));
            } else if (editIngVal == 2) {
                // ingredient amount
                ui.standard();
                System.out.println(recips.get(recipIndex).getIngredients());

                int ingIndex = ui.intGetter() - 1;
                while (ingIndex >= recips.get(recipIndex).ingredientsSize() || ingIndex == -1) {
                    ui.wronger();
                    System.out.println("Please Try To Insert A Right Value");
                    ingIndex = ui.intGetter() - 1;
                }
                ui.newValue();
                recips.get(recipIndex).editIngredientAmount(ingIndex);

            } else if (editIngVal == 3) {
                // ingredient comment
                ui.standard();
                System.out.println(recips.get(recipIndex).getIngredients());
                int ingIndex = ui.intGetter() - 1;
                while (ingIndex >= recips.get(recipIndex).ingredientsSize() || ingIndex == -1) {
                    ui.wronger();
                    System.out.println("Please Try To Insert A Right Value");
                    ingIndex = ui.intGetter() - 1;
                }
                ui.newValue();
                recips.get(recipIndex).editIngredientComment(ingIndex);

            } else if (editIngVal == 4) {
                // remove ingredient
                ui.standard();
                System.out.println(recips.get(recipIndex).getIngredients());
                int ingIndex = ui.intGetter() - 1;
                while (ingIndex >= recips.get(recipIndex).ingredientsSize() || ingIndex == -1) {
                    ui.wronger();
                    System.out.println("Please Try To Insert A Right Value");
                    ingIndex = ui.intGetter() - 1;
                }
                recips.get(recipIndex).removeIngredientFromRecip(ingIndex);

            } else {
                ui.wronger();
            }
        }
    }

    private void run() {
        opening();
        int choose = 100;

        while (choose != 0) {
            ui.menu();
            choose = ui.intGetter();
            if (choose == 1) {
                // ingredients
                ingredientMenu();
            } else if (choose == 2) {
                recipMenu();
            }
        }

        closing();

    }

    public static void main(String[] args) {
        TheApp a = new TheApp();
        a.run();
    }
}