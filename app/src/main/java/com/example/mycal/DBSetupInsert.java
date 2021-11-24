package com.example.mycal;

import android.content.Context;
import android.database.sqlite.SQLiteException;

public class DBSetupInsert {
    /* Variables */
    private final Context context;

    /* Public Class ------------------------------------------------------ */
    public DBSetupInsert(Context ctx){
        this.context = ctx;
    }

    /* Setup Insert To Categories ----------------------------------------- */
    // To insert to category table
    public void setupInsertToCategories(String values){
        try{
            DBAdapter db = new DBAdapter(context);
            db.open();
            db.insert("categories",
                    "_id, category_name, category_parent_id",
                    values);
            db.close();
        }
        catch (SQLiteException e){
            // Toast.makeText(context, "Error; Could not insert categories.", Toast.LENGTH_SHORT).show();
        }
    }
    public void insertAllCategories(){
        setupInsertToCategories("NULL, 'Roti', '0'");
        setupInsertToCategories("NULL, 'Roti Tawar', '1'");
        setupInsertToCategories("NULL, 'Roti Gandum', '1'");

        // Parent id: 6
        setupInsertToCategories("NULL, 'Kue', '0'");
        setupInsertToCategories("NULL, 'Biskuit', '4'");
        setupInsertToCategories("NULL, 'Kue Basah', '4'");
        setupInsertToCategories("NULL, 'Kue Kering', '4'");


        setupInsertToCategories("NULL, 'Minuman', '0'");
        setupInsertToCategories("NULL, 'Soda', '8'");
        setupInsertToCategories("NULL, 'Jus', '8'");
        setupInsertToCategories("NULL, 'Kopi', '8'");
        setupInsertToCategories("NULL, 'Teh', '8'");


        setupInsertToCategories("NULL, 'Buah dan Sayuran', '0'");
        setupInsertToCategories("NULL, 'Buah', '13'");
        setupInsertToCategories("NULL, 'Sayur', '13'");
        setupInsertToCategories("NULL, 'Buah kemasan kaleng', '13'");


        setupInsertToCategories("NULL, 'Daging', '0'");
        setupInsertToCategories("NULL, 'Daging Sapi', '17'");
        setupInsertToCategories("NULL, 'Daging Ayam', '17'");
        setupInsertToCategories("NULL, 'Daging Kambing', '17'");


        setupInsertToCategories("NULL, 'Susu dan Telur', '0'");
        setupInsertToCategories("NULL, 'Telur', '21'");
        setupInsertToCategories("NULL, 'Cream', '21'");
        setupInsertToCategories("NULL, 'Yogurt', '21'");
        setupInsertToCategories("NULL, 'Susu', '21'");


        setupInsertToCategories("NULL, 'Karbohidrat', '0'");
        setupInsertToCategories("NULL, 'Pizza', '26'");
        setupInsertToCategories("NULL, 'Mie', '26'");
        setupInsertToCategories("NULL, 'Pasta', '26'");
        setupInsertToCategories("NULL, 'Nasi', '26'");
        setupInsertToCategories("NULL, 'Taco', '26'");


        setupInsertToCategories("NULL, 'Keju', '0'");
        setupInsertToCategories("NULL, 'Keju Krim', '32'");
        setupInsertToCategories("NULL, 'Keju Cheddar', '32'");


        setupInsertToCategories("NULL, 'Snack', '0'");
        setupInsertToCategories("NULL, 'Kacang', '35'");
        setupInsertToCategories("NULL, 'Kripik', '35'");
    }

    /* Setup Insert To Food ----------------------------------------------- */
    // To insert food to food table
    public void setupInsertToFood(String values){

        try {
            DBAdapter db = new DBAdapter(context);
            db.open();
            db.insert("food",
                    "_id, food_name, food_manufactor_name, food_serving_size_gram, food_serving_size_gram_mesurment, food_serving_size_pcs, food_serving_size_pcs_mesurment, food_energy, food_proteins, food_carbohydrates, food_fat, food_energy_calculated, food_proteins_calculated, food_carbohydrates_calculated, food_fat_calculated, food_user_id, food_barcode, food_category_id, food_thumb, food_image_a, food_image_b, food_image_c, food_notes",
                    values);
            db.close();
        }
        catch (SQLiteException e){
            // Toast.makeText(context, "Error; Could not insert food.", Toast.LENGTH_SHORT).show();
        }

    }
    // Insert all food into food database
    public void insertAllFood(){
        setupInsertToFood("NULL, 'Roti Tawar', 'Sari Roti', '100', 'gram', '1', 'pcs', '265', '9', '49', '3.2', '265', '9', '49', '3.2', NULL, NULL, '2', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Roti gandum', 'Sari Roti', '100', 'gram', '1', 'pcs', '247', '13', '41', '3.2', '247', '13', '41', '3.2', NULL, NULL, '3', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Kue Marie Regal', 'Marie Biscuits', '25', 'gram', '1', 'pcs', '110', '3', '19', '2', '110', '3', '19', '2', NULL, NULL, '5', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Kue Muffin', 'NULL', '100', 'gram', '1', 'pcs', '227', '1.69', '44.17', '8', '227', '1.69', '44.17', '8', NULL, NULL, '6', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Coca-Cola', 'Coca-Cola', '425', 'ml', '1', 'botol', '170', '0', '43', '8', '170', '0', '43', '8', NULL, NULL, '9', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Alpukat', 'NULL', '100', 'gram', '1', 'buah', '322', '29', '17', '4', '322', '29', '17', '4', NULL, NULL, '14', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Pisang', 'NULL', '70', 'gram', '1', 'buah', '105', '0', '27', '1', '105', '0', '27', '1', NULL, NULL, '14', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Apple Juice', 'ABC', '250', 'ml', '1', 'pak', '36', '0', '7', '1.7', '36', '0', '7', '1.7', NULL, NULL, '10', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Sayur Bening Bayam', 'NULL', '100', 'gram', '1', 'porsi', '130', '0', '31', '0', '130', '0', '31', '0', NULL, NULL, '15', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Dada Ayam', 'NULL', '100', 'gram', '1', 'potong', '195', '7', '0', '29', '195', '7', '0', '29', NULL, NULL, '19', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
        setupInsertToFood("NULL, 'Dada Sapi', 'NULL', '85', 'gram', '1', 'porsi', '245', '17', '0', '22', '245', '17', '0', '22', NULL, NULL, '18', 'NULL', 'NULL', 'NULL', 'NULL', NULL");
    }
}