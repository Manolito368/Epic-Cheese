package manolitowo.cheese.mod;





import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class CheeseMod implements ModInitializer {
//items and blocks
	public static final Item CHEESE = new Item(new Item.Settings().group(ItemGroup.FOOD));
	public static final Block CHEESE_BLOCK = new Block(FabricBlockSettings.of(Material.SOIL).strength(0.5F, 0.5F).sounds(BlockSoundGroup.CORAL).breakByTool(FabricToolTags.HOES));
	public static final Item CHEESE_BREAD = new Item(new Item.Settings().group(ItemGroup.FOOD).food(CheeseFoodComponents.CHEESE_BREAD));
	public static final Item MELTED_CHEESE_BREAD = new Item(new Item.Settings().group(ItemGroup.FOOD).food(CheeseFoodComponents.MELTED_CHEESE_BREAD));
	public static final Block CHEESE_ORE = new CheeseOre(FabricBlockSettings.of(Material.SOIL).strength(2.5F, 2.5F).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES));

	private static ConfiguredFeature<?,?> CHEESE_ORE_OVERWORLD = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, CHEESE_ORE.getDefaultState(), 4))
	.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(55))))).spreadHorizontally().repeat(15);

	//biome
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CHEESE_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
    .withConfig(new TernarySurfaceConfig(
      Blocks.GRASS_BLOCK.getDefaultState(),
      Blocks.DIRT.getDefaultState(),
      Blocks.GRAVEL.getDefaultState()));

	  private static final Biome CHEESE_PLAINS = createCheesePlainsBiome();
 
	  private static Biome createCheesePlainsBiome() {
	 
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
		DefaultBiomeFeatures.addMonsters(spawnSettings, 95, 5, 100);
	 
		var generationSettings = new GenerationSettings.Builder();
		generationSettings.surfaceBuilder(CHEESE_SURFACE_BUILDER);
		DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
		DefaultBiomeFeatures.addLandCarvers(generationSettings);
		DefaultBiomeFeatures.addDungeons(generationSettings);
		DefaultBiomeFeatures.addMineables(generationSettings);
		DefaultBiomeFeatures.addDefaultOres(generationSettings);
		DefaultBiomeFeatures.addDefaultDisks(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
		DefaultBiomeFeatures.addDripstone(generationSettings);
		DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
	 
		return (new Biome.Builder())
		  .precipitation(Biome.Precipitation.RAIN)
		  .category(Biome.Category.PLAINS)
		  .depth(0.125F)
		  .scale(0.05F)
		  .temperature(0.8F)
		  .downfall(0.4F)
		  .effects((new BiomeEffects.Builder())
			.waterColor(0xF9D73F)
			.waterFogColor(0xF9D73F)
			.fogColor(0xF9C11E)
			.skyColor(0xFFE738)
			.foliageColor(0xFFDD00)
			.grassColor(0xFFDD00)
			.build())
		  .spawnSettings(spawnSettings.build())
		  .generationSettings(generationSettings.build())
		  .build();}

		  public static final RegistryKey<Biome> CHEESE_PLAINS_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier("cheesemod", "cheese_plains"));


	@Override
	public void onInitialize() {
	
		Registry.register(Registry.ITEM, new Identifier("cheesemod", "cheese"), CHEESE);
		Registry.register(Registry.BLOCK, new Identifier("cheesemod", "cheese_block"), CHEESE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("cheesemod", "cheese_block"), new BlockItem(CHEESE_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("cheesemod", "cheese_bread"), CHEESE_BREAD);
		Registry.register(Registry.ITEM, new Identifier("cheesemod", "melted_cheese_bread"), MELTED_CHEESE_BREAD);
		Registry.register(Registry.BLOCK, new Identifier("cheesemod", "cheese_ore"), CHEESE_ORE);
		Registry.register(Registry.ITEM, new Identifier("cheesemod", "cheese_ore"), new BlockItem(CHEESE_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

		RegistryKey<ConfiguredFeature<?, ?>> cheeseOreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("cheesemod", "cheese_ore"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, cheeseOreOverworld.getValue(), CHEESE_ORE_OVERWORLD);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, cheeseOreOverworld);

		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier("cheesemod", "cheese_s"), CHEESE_SURFACE_BUILDER);
    	Registry.register(BuiltinRegistries.BIOME, CHEESE_PLAINS_KEY.getValue(), CHEESE_PLAINS);
		//OverworldBiomes.addContinentalBiome(CHEESE_PLAINS_KEY, OverworldClimate.TEMPERATE, 2D);
		//OverworldBiomes.addContinentalBiome(CHEESE_PLAINS_KEY, OverworldClimate.COOL, 2D);
	}
}
