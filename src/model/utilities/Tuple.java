package model.utilities;

public class Tuple<First, Last> {

    private First f;
    private Last l;

    public Tuple(First f, Last l){
        this.f = f;
        this.l = l;
    }

    public First getFirst(){
        return f;
    }

    public Last getLast(){
        return l;
    }
}
