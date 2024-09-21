package net.minecraft.client;

public class ClientBrandRetriever
{
    private static String clientBrand = "lolaclient-1.8.9";
    public static String getClientModName()
    {
        return clientBrand;
    }

    public static void setClientBrand(String brand) {
        clientBrand = brand;
    }
}
