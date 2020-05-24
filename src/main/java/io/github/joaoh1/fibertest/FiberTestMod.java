package io.github.joaoh1.fibertest;

import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class FiberTestMod implements ClientModInitializer {
	public static final PropertyMirror<Boolean> innocentBoolean = PropertyMirror.create(ConfigTypes.BOOLEAN);
	
	public static final ConfigTree tree = ConfigTree.builder()
		.beginValue("innocent_boolean", ConfigTypes.BOOLEAN, false)
		.finishValue(innocentBoolean::mirror)
		.build();
	
	public static final FabricKeyBinding setBooleanToFalseKeyBinding = FabricKeyBinding.Builder
		.create(new Identifier("fibertest", "set_boolean_to_false"), InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "key.fibertest.category")
		.build();

	public static final FabricKeyBinding setBooleanToTrueKeyBinding = FabricKeyBinding.Builder
		.create(new Identifier("fibertest", "set_boolean_to_true"), InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "key.fibertest.category")
		.build();

	@Override
	public void onInitializeClient() {
		KeyBindingRegistry.INSTANCE.addCategory("key.fibertest.category");
		KeyBindingRegistry.INSTANCE.register(setBooleanToFalseKeyBinding);
		KeyBindingRegistry.INSTANCE.register(setBooleanToTrueKeyBinding);
		
		ClientTickCallback.EVENT.register(e -> {
			if (setBooleanToFalseKeyBinding.isPressed()) {
				innocentBoolean.setValue(false);
			} else if (setBooleanToTrueKeyBinding.isPressed()) {
				innocentBoolean.setValue(true);
			}

			if (innocentBoolean.getValue() == null) {
				System.out.println("The config's boolean is null!");
			}
		});
	}
}
