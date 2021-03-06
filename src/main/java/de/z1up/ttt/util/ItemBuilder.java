package de.z1up.ttt.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * The ItemBuilder can create new ItemStacks that can
 * be submitted via the build method.
 *
 * @author chris23lngr
 * @since 1.0
 * @see org.bukkit.inventory.ItemStack
 * @see org.bukkit.Material
 */
public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private SkullMeta skullMeta;

    public ItemBuilder(Material material, int id) {
        this.itemStack = new ItemStack(material, 1, (short) id);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, Short id)
    {
        this.itemStack = new ItemStack(material, 1, id);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(String skullOwner) {
        this.itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        this.skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        this.skullMeta.setOwner(skullOwner);
    }

    public ItemBuilder(Integer id, Short subId)
    {
        this.itemStack = new ItemStack(id, 1, subId); this.itemMeta = this.itemStack.getItemMeta();
    }


    /**
     * add a Enchantment to the ItemStack
     * @param enchantment
     * @param amount
     * @return ItemBuilder
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, Integer amount)
    {
        if (this.itemMeta != null)
        {
            this.itemMeta.addEnchant(enchantment, amount, true);
        }
        else
        {
            this.skullMeta.addEnchant(enchantment, amount, true);
        }
        return this;
    }

    /**
     * add a ItemFlag to the ItemStack
     * @param itemFlag
     * @return ItemBuilder
     */
    public ItemBuilder addItemFlag(ItemFlag itemFlag)
    {
        if (this.itemMeta != null)
        {
            this.itemMeta.addItemFlags(itemFlag);
        }
        else
        {
            this.skullMeta.addItemFlags(itemFlag);
        }
        return this;
    }

    /**
     * set the displayname of the ItemStack
     * @param name
     * @return ItemBuilder
     */
    public ItemBuilder setDisplayName(String name)
    {
        if (this.itemMeta != null)
        {
            this.itemMeta.setDisplayName(name);
        }
        else
        {
            this.skullMeta.setDisplayName(name);
        }
        return this;
    }

    /**
     * set the lore of the ItemStack with a List
     * @param loreName
     * @return ItemBuilder
     */
    public ItemBuilder setLore(String... loreName) {
        List<String> customLore = new ArrayList<String>();
        for (String Lore : loreName)
        {
            customLore.add(Lore);
        }
        if (this.itemMeta != null)
        {
            this.itemMeta.setLore(customLore);
        }
        else
        {
            this.skullMeta.setLore(customLore);
        }
        return this;
    }

    /**
     * finally builds the ItemStack
     * @return itemStack
     */
    public ItemStack build()
    {
        if (this.itemMeta != null)
        {
            this.itemStack.setItemMeta(this.itemMeta);
        }
        else
        {
            this.itemStack.setItemMeta(skullMeta);
        }
        return this.itemStack;
    }
}