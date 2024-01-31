package com.gestaoCash.configs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.ItemSale;
import com.gestaoCash.model.Sale;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import aj.org.objectweb.asm.Type;


public class ListSaleTypeAdapter implements JsonSerializer<List<Sale>>, JsonDeserializer<List<Sale>> {

 
	@Override
	public List<Sale> deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		  List<Sale> sales = context.deserialize(json, new TypeToken<List<Sale>>() {}.getType());

	        // You may need to handle deserialization logic if necessary

	        return sales;
	        
	        
	}

	@Override
	public JsonElement serialize(List<Sale> sales, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
		  JsonArray jsonArray = new JsonArray();
	        
	        for (Sale sale : sales) {
	            JsonObject saleObject = new JsonObject();
	            
	            // Serialize Sale fields
	            saleObject.addProperty("id", sale.getId());
	            saleObject.addProperty("valorTotal", sale.getValorTotal()); // Assuming LocalDate is stored as a String
	            saleObject.addProperty("data", sale.getData().toString());
	            // Serialize Client
	            JsonObject clientObject = new JsonObject();
	            clientObject.addProperty("clientId", sale.getCliente().getId());
	            clientObject.addProperty("clientName", sale.getCliente().getNome());
	            clientObject.addProperty("dataNascimento", sale.getCliente().getDataNascimento().toString()); // Assuming LocalDate is stored as a String
	            saleObject.add("client", clientObject);
	            
	            // Serialize Item List
	            JsonArray itemListArray = context.serialize(sale.getItemSale(), new TypeToken<List<ItemSale>>() {}.getType()).getAsJsonArray();
	            saleObject.add("itemList", itemListArray);
	            
	            jsonArray.add(saleObject);
	        }
		return null;
	}
}
