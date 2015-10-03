package com.biel.FastSurvival.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * Utility class to compare Bukkit recipes.<br>
 * Useful for identifying your recipes in events, where recipes are re-generated in a diferent manner.
 * 
 * @version R1.3
 * @author Digi
 */
public class RecipeUtils
{
    /**
     * The wildcard data value for ingredients.<br>
     * If this is used as data value on an ingredient it will accept any data value.
     */
    public static final short DATA_WILDCARD = Short.MAX_VALUE;
    
    /**
     * Checks if both recipes are equal.<br>
     * Compares both ingredients and results.<br>
     * <br>
     * NOTE: If both arguments are null it returns true.
     * 
     * @param recipe1
     * @param recipe2
     * @return true if ingredients and results match, false otherwise.
     * @throws IllegalArgumentException
     *             if recipe is other than ShapedRecipe, ShapelessRecipe or FurnaceRecipe.
     */
    public static boolean areEqual(Recipe recipe1, Recipe recipe2)
    {
        if(recipe1 == recipe2)
        {
            return true; // if they're the same instance (or both null) then they're equal.
        }
        
        if(recipe1 == null || recipe2 == null)
        {
            return false; // if only one of them is null then they're surely not equal.
        }
        
        if(!recipe1.getResult().equals(recipe2.getResult()))
        {
            return false; // if results don't match then they're not equal.
        }
        
        return match(recipe1, recipe2); // now check if ingredients match
    }
    
    /**
     * Checks if recipes are similar.<br>
     * Only checks ingredients, not results.<br>
     * <br>
     * NOTE: If both arguments are null it returns true. <br>
     * 
     * @param recipe1
     * @param recipe2
     * @return true if ingredients match, false otherwise.
     * @throws IllegalArgumentException
     *             if recipe is other than ShapedRecipe, ShapelessRecipe or FurnaceRecipe.
     */
    public static boolean areSimilar(Recipe recipe1, Recipe recipe2)
    {
        if(recipe1 == recipe2)
        {
            return true; // if they're the same instance (or both null) then they're equal.
        }
        
        if(recipe1 == null || recipe2 == null)
        {
            return false; // if only one of them is null then they're surely not equal.
        }
        
        return match(recipe1, recipe2); // now check if ingredients match
    }
    
    private static boolean match(Recipe recipe1, Recipe recipe2)
    {
        if(recipe1 instanceof ShapedRecipe)
        {
            if(recipe2 instanceof ShapedRecipe == false)
            {
                return false; // if other recipe is not the same type then they're not equal.
            }
            
            ShapedRecipe r1 = (ShapedRecipe)recipe1;
            ShapedRecipe r2 = (ShapedRecipe)recipe2;
            
            // convert both shapes and ingredient maps to common ItemStack array.
            ItemStack[] matrix1 = shapeToMatrix(r1.getShape(), r1.getIngredientMap());
            ItemStack[] matrix2 = shapeToMatrix(r2.getShape(), r2.getIngredientMap());
            
            if(!Arrays.equals(matrix1, matrix2)) // compare arrays and if they don't match run another check with one shape mirrored.
            {
                mirrorMatrix(matrix1);
                
                return Arrays.equals(matrix1, matrix2);
            }
            
            return true; // ingredients match.
        }
        else if(recipe1 instanceof ShapelessRecipe)
        {
            if(recipe2 instanceof ShapelessRecipe == false)
            {
                return false; // if other recipe is not the same type then they're not equal.
            }
            
            ShapelessRecipe r1 = (ShapelessRecipe)recipe1;
            ShapelessRecipe r2 = (ShapelessRecipe)recipe2;
            
            // get copies of the ingredient lists
            List<ItemStack> find = r1.getIngredientList();
            List<ItemStack> compare = r2.getIngredientList();
            
            if(find.size() != compare.size())
            {
                return false; // if they don't have the same amount of ingredients they're not equal.
            }
            
            for(ItemStack item : compare)
            {
                if(!find.remove(item))
                {
                    return false; // if ingredient wasn't removed (not found) then they're not equal.
                }
            }
            
            return find.isEmpty(); // if there are any ingredients not removed then they're not equal.
        }
        else if(recipe1 instanceof FurnaceRecipe)
        {
            if(recipe2 instanceof FurnaceRecipe == false)
            {
                return false; // if other recipe is not the same type then they're not equal.
            }
            
            FurnaceRecipe r1 = (FurnaceRecipe)recipe1;
            FurnaceRecipe r2 = (FurnaceRecipe)recipe2;
            
            //return (r1.getInput().equals(r2.getInput())); // TODO use this when furnace data PR is pulled
            return r1.getInput().getTypeId() == r2.getInput().getTypeId();
        }
        else
        {
            throw new IllegalArgumentException("Unsupported recipe type: '" + recipe1 + "', update this class!");
        }
    }
    
    private static ItemStack[] shapeToMatrix(String[] shape, Map<Character, ItemStack> map)
    {
        ItemStack[] matrix = new ItemStack[9];
        int slot = 0;
        
        for(int r = 0; r < shape.length; r++)
        {
            for(char col : shape[r].toCharArray())
            {
                matrix[slot] = map.get(col);
                slot++;
            }
            
            slot = ((r + 1) * 3);
        }
        
        return matrix;
    }
    
    private static void mirrorMatrix(ItemStack[] matrix)
    {
        ItemStack tmp;
        
        for(int r = 0; r < 3; r++)
        {
            tmp = matrix[(r * 3)];
            matrix[(r * 3)] = matrix[(r * 3) + 2];
            matrix[(r * 3) + 2] = tmp;
        }
    }
}