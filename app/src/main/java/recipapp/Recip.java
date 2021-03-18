package recipapp;

import java.util.ArrayList;

public class Recip {

  private String name;
  private ArrayList<Ingredient> ingredients;
  private ArrayList<Double> meny;
  private ArrayList<String> comments;
  private UiConsoleSc ui = new UiConsoleSc();
  private ArrayList<String> wayToMake;
  private ArrayList<ArrayList<?>> recipt;
  private int portions;

  /**
   * Recip Object.
   */
  public Recip(String name) {
    this.name = name;
    ingredients = new ArrayList<>();
    meny = new ArrayList<>();
    comments = new ArrayList<>();
    recipt = new ArrayList<>();
    wayToMake = new ArrayList<>();

  }

  public void makeWayFromFile(String steg) {
    wayToMake.add(steg);
  }

  public void portionSetter(int portions) {
    this.portions = portions;
  }

  public int getPortions() {
    return portions;
  }

  /**
   * the method called when the recip is done
   * and making way is asked.
   */
  public void makingWay() {
    do {
      System.out.println("The Steps!\n");
      System.out.println(viewWayMake());
      System.out.println("\n\nNew Step To Do When Doing Recip?  (N) When Finish");
      wayToMake.add(ui.stringGetter());
      System.out.println("New Step Have Been Added!");
    } while (!(ui.stringGetter().equalsIgnoreCase("n")));
  }

  /**
   * adds ingredient to the recip from the user.
   */
  public void addIngredient(ArrayList<Ingredient> ingredientz) {
    System.out.println("ADD new ingredient!");
    String more = "";
    while (!(more.equalsIgnoreCase("n"))) {
      int x = 0;
      for (Ingredient c : ingredientz) {
        x++;
        System.out.print(x + ". " + c.getName());
        System.out.println();
      }
      System.out.println("\n\n");

      final int theIngredient = ui.compare(ingredientsSize());

      System.out.println("How Much?");
      double much = ui.doubleGetter();

      System.out.println("What Reason");
      String reason = ui.stringGetter();

      comments.add(reason);
      meny.add(much);
      ingredients.add(ingredientz.get(theIngredient));

      recipt.add(ingredients);
      recipt.add(meny);
      recipt.add(comments);

      System.out.println("ADD new ingredient! (N for No)");
      more = ui.stringGetter();
    }
  }

  public String getName() {
    return name;
  }

  /**
   * gets the ingredients with the the amount
   * and reason as string.
   */
  public String getIngredients() {
    int y = 1;
    StringBuilder x = new StringBuilder();
    for (int c = 0; c < ingredients.size(); c++) {
      x.append(y);
      x.append(". " + ingredients.get(c).getName() + " : " + meny.get(c) 
          + " (" + ingredients.get(c).getUnit() + ")"
          + " : " + "(Reason) " + comments.get(c) + "\n");
      y++;
    }
    return x.toString();
  }

  /**
   * get the the price of the recip.
   */
  public int getPrice() {
    int x = 0;
    for (Ingredient c : ingredients) {
      x += c.getPrice();
    }
    return x;
  }

  /**
   * the method called only from FileManager class
   * to write the recip to the file.
   */
  public String writeRecip() {
    StringBuilder x = new StringBuilder();
    for (int c = 0; c < ingredients.size(); c++) {
      x.append(ingredients.get(c).getName() + " : " + meny.get(c) + " : " + comments.get(c) + "||");
    }
    x.append(",");
    for (String step : wayToMake) {
      x.append(step + "||");
    }
    x.append(",");
    x.append(getPortions());
    return x.toString();
  }

  /**
   * get the steps to make as string.
   */
  public String viewWayMake() {
    int y = 1;
    StringBuilder x = new StringBuilder();
    for (String c : wayToMake) {
      x.append(y + ". ");
      x.append(c + "\n");
      y++;
    }
    return x.toString();
  }
  
  /**
   * edits the recip name.
   */
  public void editName() {
    System.out.println("What is the new name of the recip " + getName());
    String newName = ui.stringGetter();
    name = newName;

  }
  
  public void editIngredientAmount(int index) {
    meny.set(index, ui.doubleGetter());

  }

  public void editIngredientComment(int index) {
    comments.set(index, ui.stringGetter());

  }

  /**
   * remove an ingredietn from the recip.
   */
  public void removeIngredientFromRecip(int index) {
    System.out.println("Are you sure (y)");
    if (ui.stringGetter().equalsIgnoreCase("y")) {
      ingredients.remove(index);
      meny.remove(index);
      comments.remove(index);
    } else {
      System.out.println("Opretion Aborted");
    }
  }

  public int ingredientsSize() {
    return ingredients.size();
  }

  /**
   * Adds step to the specifid place.
   */ 
  public void addStepAtPlace() {
    System.out.println("\nWhere Should It Be Added:\n");
    System.out.println(viewWayMake());
    int place = ui.compare(wayToMake.size());

    System.out.println("What Is The New Step:\n");
    wayToMake.add(place, ui.stringGetter());

  }

  /**
   * removes a step from the recip.
   */
  public void removeStep() {
    System.out.println("\nWhere Should It Be Removed:\n");
    System.out.println(viewWayMake());

    int place = ui.compare(wayToMake.size());

    System.out.println("Are you sure (y)");
    if (ui.stringGetter().equalsIgnoreCase("y")) {
      wayToMake.remove(place);
    } else {
      System.out.println("Opretion Aborted");
    }
  }

  /**
   * changes a step in the making way from the recip.
   */
  public void editStep() {
    System.out.println("\nWhere Should It Be Edited:\n");
    System.out.println(viewWayMake());
    int place = ui.compare(wayToMake.size());

    System.out.println("What Is The New Step:\n");
    wayToMake.set(place, ui.stringGetter());

  }

  /**
   * checks if an ingredient is in this recip.
   */
  public boolean haveIngredient(String ingr) {

    for (Ingredient ingredient : ingredients) {
      if (ingredient.getName().equals(ingr)) {
        return true;
      }
    }
    return false;
  }

  /**
   * called from class FileManagar to add ingredietns from file.
   */
  public void addIngredientFromFile(Ingredient ingr, double much, String reason) {
    comments.add(reason);
    meny.add(much);
    ingredients.add(ingr);

    recipt.add(ingredients);
    recipt.add(meny);
    recipt.add(comments);
  }
}