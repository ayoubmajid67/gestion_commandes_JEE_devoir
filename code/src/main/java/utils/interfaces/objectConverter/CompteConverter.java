package utils.interfaces.objectConverter;


import model.Compte;


public class CompteConverter implements ObjectConverter<Compte> {
    @Override
    public Compte convertObject(){
        return new Compte();
    }
}
