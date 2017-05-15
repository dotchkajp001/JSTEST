import com.google.gson.JsonObject;

/**
 *
 * @author opengion
 *
 * gsonのライブラリ読み込みが必要
 */
public class JavaHello {
	public static JsonObject main(JsonObject args){
		String name = "stranger";
		if (args.has("name"))
			name = args.getAsJsonPrimitive("name").getAsString();
		JsonObject response = new JsonObject();
		response.addProperty("greeting",  "Hello " + name + "!");
		response.addProperty("2", "test2");

		JsonObject obj = new JsonObject();
		obj.addProperty("ko", "kovakue");
		response.add("ko", obj);
		return response;
	}
}
