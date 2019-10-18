package pl.coderslab.model;

public class PlanCollect {
    private String dayName;
    private String mealName;
    private String description;
    private int recipeId;
    private int recipePlanId;

    public PlanCollect() {
    }

    public PlanCollect(String dayName, String mealName, String description, int recipeId, int recipePlanId) {
        this.dayName = dayName;
        this.mealName = mealName;
        this.description = description;
        this.recipeId = recipeId;
        this.recipePlanId = recipePlanId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipePlanId() {
        return recipePlanId;
    }

    public void setRecipePlanId(int recipePlanId) {
        this.recipePlanId = recipePlanId;
    }
}
