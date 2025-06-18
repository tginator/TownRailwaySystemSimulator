package edu.curtin.oose2024s2.assignment2;

import java.util.ArrayList;
import java.util.List;

public class Build<T> {

    // Type of build which will be used to generalise a set of towns/railways/observers.
    private final List<T> builds;

    public Build()
    {
        builds = new ArrayList<>();
    }

    public void add( T build )
    {
        builds.add( build );
    }

    // Universal method to retrieve set of generic type.
    public List<T> getList()
    {
        return builds;
    }

}
