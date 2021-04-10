package com.mediscreen.services;

import com.mediscreen.domain.Patient;

import java.util.List;

public interface ICrudService<T>  {

    /**
     *Add and save to database method
     * @param objet to add
     * @return created object
     */
    T add(T objet);

    /**
     * Read all the entries in bdd
     * @return list object
     */
    List<T> readAll();

    /**
     * Update the objects in the database
     * @param id of the object to modify
     * @param objet update
     * @return updating the object
     */
    T update(Integer id,T objet);


    /**
     * Delete object in the database
     * @param id object to delete
     */
    void delete(Integer id);

}
