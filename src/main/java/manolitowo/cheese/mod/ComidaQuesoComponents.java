package manolitowo.cheese.mod;

import net.minecraft.item.FoodComponent;

public class ComidaQuesoComponents {

    public static final FoodComponent PAN_QUESO = (new FoodComponent.Builder()).hunger(6).saturationModifier(1).build();
    public static final FoodComponent QUESO_DERRETIDO = (new FoodComponent.Builder()).hunger(8).saturationModifier((float) 1.5).build();
    
}
