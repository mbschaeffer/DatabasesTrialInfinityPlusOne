package com.kiskiarea.databasestrialinfinityplusone;

/**
 * Created by Melissa on 11/15/2015.
 */
public class Element {

    private int _atomic_number;
    private double _atomic_weight;
    private String _name;
    private String _symbol;


    //No arg constructor
    public Element()
    {

    }


    //Constructor
    public Element(int atomic_number, double atomic_weight, String name, String symbol)
    {
        this._atomic_number = atomic_number;
        this._atomic_weight = atomic_weight;
        this._name = name;
        this._symbol = symbol;


    }

    /*---------------
    *     SETTERS
    *---------------*/

    public void set_atomic_number(int atomic_number)
    {
        this._atomic_number = atomic_number;
    }

    public void set_atomic_weight(double atomicWeight)
    {
        this._atomic_weight = atomicWeight;
    }

    public void set_name(String name)
    {
        this._name = name;
    }

    public void set_symbol(String symbol)
    {
        this._symbol = symbol;
    }


    /*---------------
    *     GETTERS
    *---------------*/
    public int get_atomic_number()
    {
        return this._atomic_number;
    }

    public double get_atomic_weight()
    {
        return this._atomic_weight;
    }

    public String get_name()
    {
        return this._name;
    }

    public String get_symbol()
    {
        return this._symbol;
    }

}
