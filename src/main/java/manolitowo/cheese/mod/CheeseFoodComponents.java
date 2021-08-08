package manolitowo.cheese.mod;

import net.minecraft.item.FoodComponent;

public class CheeseFoodComponents {

    public static final FoodComponent CHEESE_BREAD = (new FoodComponent.Builder()).hunger(6).saturationModifier(1).build();
    public static final FoodComponent MELTED_CHEESE_BREAD = (new FoodComponent.Builder()).hunger(8).saturationModifier((float) 1.5).build();
    
}
