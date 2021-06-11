package com.zombiecastlerush.building;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * this class provides services that convert a single Item object or a list of Item objects into a Json String or .json file
 */
class ItemFactory {
    /**
     * read items from a .json file and convert it into a list of Item objects
     * this function can assign above list of items to Inventory reference
     * it can also return above list of items
     *
     * @param inventory reference that wants to save this list of Items
     * @param file      where we save the .json file
     * @return a list of Items
     * @throws IOException
     */
    public static List<Item> readItemsFromDir(Inventory inventory, File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Invalid output file path");
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Item> list = Arrays.asList(mapper.readValue(file, Item[].class));
        if (inventory == null) {
            throw new IllegalArgumentException("Invalid Inventory reference");
        } else {
            inventory.setItems(list);
        }
        return list; // list can be null
    }

    /**
     * write a list of Items to a .json file
     *
     * @param file  target output file
     * @param items a list of items
     * @throws IOException
     */
    public static void writeItemsToDir(File file, List<Item> items) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Invalid output file path");
        }
        ObjectMapper mapper = new ObjectMapper();
        if (!file.exists()) {
            file.createNewFile(); // create one if not exist
        }
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(file, items);
    }

    /**
     * convert String Json format to an Item object
     *
     * @param jsonItem
     * @return
     * @throws JsonProcessingException
     */
    public static Item loadItem(String jsonItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonItem, Item.class);
    }

    /**
     * convert an Item to a json string
     *
     * @param item   the Item object
     * @param pretty true prints pretty json
     * @return a json string if item is not null
     * @throws JsonProcessingException
     */
    public static String itemToJson(Item item, boolean pretty) throws JsonProcessingException {
        if (Objects.isNull(item)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        if (pretty) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);
        } else {
            return mapper.writeValueAsString(item);
        }
    }
}
