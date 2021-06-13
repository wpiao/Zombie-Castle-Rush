package com.zombiecastlerush.building;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemFactoryTest {
    private List<Item> items = new ArrayList<>();
    private String outputPath = System.getProperty("user.dir").concat("\\target\\items.json");
    ;

    @Before
    public void setUp() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(outputPath);
        File file = new File(outputPath);
        for (int i = 0; i < 10; i++) {
            Item item = new Item("item" + i, "i am #" + i + " item", 50 + i);
            items.add(item);
        }
        ItemFactory.writeItemsToDir(file, items); // generate .json file
    }

    @Test
    public void testReadItemsFromDirectory_returnAListOfItems() throws IOException {
        Inventory inventory = new Inventory();
        File file = new File(outputPath);
        List<Item> list = ItemFactory.readItemsFromDir(inventory, file);
        for (int i = 0; i < list.size(); i++) {
            // expectedItem is how the Item class implements toString(), but manually made; needs to change accordingly
            String expectedItem = String.format("name= item%d description= i am #%d item", i, i);
            System.out.println(list.get(i).toString());
            System.out.println(expectedItem);
            Assert.assertEquals(list.get(i).toString(), expectedItem);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadItemsFromDirectory_throwExceptionIfInputFileNull() throws IOException {
        Inventory inventory = new Inventory();
        File file = null;
        ItemFactory.readItemsFromDir(inventory, file);
    }

    @Test(expected = IOException.class)
    public void testReadItemsFromDirectory_throwExceptionIfInputFileNotFound() throws IOException {
        Inventory inventory = new Inventory();
        File file = new File(outputPath.concat("badFilePath"));
        ItemFactory.readItemsFromDir(inventory, file);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadItemsFromDirectory_throwExceptionIfInventoryRefNull() throws IOException {
        Inventory inventory = new Inventory();
        File file = new File(outputPath);
        ItemFactory.readItemsFromDir(null, file);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWriteItemsToDirectory_throwExceptionIfOutputFileNull() throws IOException {
        // TODO: TEST even used it in setUp()
        ItemFactory.writeItemsToDir(null, items); // generate .json file
    }

    //TODO: more testing for writeItemsToDir()

    @Test
    public void testLoadItem_convertJsonToItemObject() throws JsonProcessingException {
        String name = "aPpLe";
        String description = "jUsT an aPpLe";
        String jsonItem = String.format("{\"name\":\"%s\",\"description\":\"%s\"}", name, description);
        // this is how the toString() overridden. need to be adjusted accordingly
        String expectedItem = String.format("name= %s description= %s", name, description);
        Item item = ItemFactory.loadItem(jsonItem);
        Assert.assertEquals(item.toString(), expectedItem);
    }

    @Test
    public void testLoadItem_convertEmptyJsonToItemObject() throws JsonProcessingException {
        String name = "";
        String description = "";
        String jsonItem = String.format("{\"name\":\"%s\",\"description\":\"%s\"}", name, description);
        // this is how the toString() overridden. need to be adjusted accordingly
        String expectedItem = String.format("name= %s description= %s", name, description);
        Item item = ItemFactory.loadItem(jsonItem);
        Assert.assertEquals(item.toString(), expectedItem);
    }

    @Test
    public void testItemToJson_convertItemObjectToJson() throws JsonProcessingException {
        String name = "iTeM2";
        String description = "jUsT aNoThEr iTeM";
        double price = 100.0;
        Item item = new Item(name, description, price);
        // json format not the toString()
        String expectedJson = String.format("{\"name\":\"%s\",\"description\":\"%s\",\"price\":%.1f}", name, description, price);
        String resultJson = ItemFactory.itemToJson(item, false);
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void testItemToJson_convertNullObjectToJson() throws JsonProcessingException {
        String resultJson = ItemFactory.itemToJson(null, false);
        Assert.assertEquals(null, resultJson);
    }

    @Test
    public void testItemToJson_convertNullAttributesToJson() throws JsonProcessingException {
        Item item = new Item(null, null, 0.0);
        String expectedJson = "{\"name\":null,\"description\":null,\"price\":0.0}";
        String resultJson = ItemFactory.itemToJson(item, false); // not pretty json
        Assert.assertEquals(expectedJson, resultJson);
    }
}
