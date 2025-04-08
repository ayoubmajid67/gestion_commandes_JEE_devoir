package utils.interfaces.objectConverter;

import model.Commande;

public class CommandeConverter implements ObjectConverter<Commande> {
    @Override
    public Commande convertObject() {
        return new Commande();
    }
}
