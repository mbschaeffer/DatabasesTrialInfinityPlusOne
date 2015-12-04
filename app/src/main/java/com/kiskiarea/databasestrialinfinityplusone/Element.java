package com.kiskiarea.databasestrialinfinityplusone;

/**
 * Created by Melissa on 11/15/2015.
 */
public class Element {

    private String _name;
    private int _atomic_number;
    private double _atomic_weight;
    private String _symbol;
    private double _melting_point;
    private double _boiling_point;
    private double _density;
    private String _phase;

    //No arg constructor
    public Element()
    {

    }

    //Constructor
    public Element(String name,int atomic_number, double atomic_weight, String symbol,
                   double boiling_point, double melting_point, double density, String phase)
    {
        this._name = name;
        this._atomic_number = atomic_number;
        this._atomic_weight = atomic_weight;
        this._symbol = symbol;
        this._melting_point = melting_point;
        this._boiling_point = boiling_point;
        this._density = density;
        this._phase = phase;


    }

    /*---------------
    *     SETTERS
    *---------------*/
    public void set_name(String name)
    {
        this._name = name;
    }
    public void set_atomic_number(int atomic_number)
    {
        this._atomic_number = atomic_number;
    }
    public void set_atomic_weight(double atomicWeight)
    {
        this._atomic_weight = atomicWeight;
    }
    public void set_symbol(String symbol)
    {
        this._symbol = symbol;
    }
    public void set_boiling_point(int boiling_point)
    {
        this._boiling_point = boiling_point;
    }
    public void set_melting_point(int melting_point)
    {
        this._melting_point = melting_point;
    }
    public void set_density(int density)
    {
        this._density = density;
    }
    public void set_phase(String phase)
    {
        this._phase = phase;
    }


    /*---------------
    *     GETTERS
    *---------------*/
    public String get_name()
    {
        return this._name;
    }
    public int get_atomic_number()
    {
        return this._atomic_number;
    }
    public double get_atomic_weight()
    {
        return this._atomic_weight;
    }
    public String get_symbol()
    {
        return this._symbol;
    }
    public double get_boiling_point()
    {
        return this._boiling_point;
    }
    public double get_melting_point()
    {
        return this._melting_point;
    }
    public double get_density()
    {
        return this._density;
    }
    public String get_phase()
    {
        return this._phase;
    }
}
