import com.google.gson.annotations.SerializedName

data class ProductType
	(
	@SerializedName("type") val type : String,
	@SerializedName("log") val log : Int,
	@SerializedName("v") val volume : Float,
	@SerializedName("w") val weight : Float
)