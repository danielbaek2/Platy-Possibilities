package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.ufund.api.ufundapi.model.Need;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CupboardFileDAO implements CupboardDAO{
    private String filename;
    private ObjectMapper objectMapper = null;
    private HashMap<Integer, Need> needs;
    /**
     * CupboardFileDao -  Current instance of the cupboard data access.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    // not sure how to recieve file name.
    public CupboardFileDAO(String filename, ObjectMapper objectmapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectmapper;
        this.needs = new HashMap<>();
        loadFile();
    }

    /**
     * loadFile - Loads the needs into a hashmap, where the key is the ID, the value is the full need structure.
     * @throws IOException
     */
    private void loadFile() throws IOException{
        Need[] needList = objectMapper.readValue(new File(filename),Need[].class);
        for (Need currNeed : needList){ // for each need, load hashmap of needs with ID and need structure.
            needs.put(currNeed.getId(), currNeed);
        }
    }

    /**
     * saveFile - Saves the hashmap
     * @throws IOException
     */
    private void saveFile() throws IOException{
        Need[] needArrayList = getNeedsArray(null);
        objectMapper.writeValue(new File(filename),needArrayList);
    }



    private Need[] getNeedsArray(){
        return getNeedsArray(null);
    }

    /**
     * Generate an array of needs from the hashmap, using a given substring as a filter.
     * 
     * @param containsText the substring we are comparing the titles of needs to.
     * 
     * @return an array of Needs, possibly empty.
     */
    private Need[] getNeedsArray(String containsText) {
        ArrayList<Need> needArrayList = new ArrayList<>();
        for (Need need : needs.values()) {
            if (containsText == null || need.getTitle().contains(containsText)) {
                needArrayList.add(need);
            }
        }
        Need[] needArray = new Need[needArrayList.size()];
        return needArrayList.toArray(needArray);
    }


    /**
     * CreateNeed - Adds the need parameter into the needs hashmap.
     * @param need - New need to be added
     * @return - If need works, return need. If not, null.
     * @throws IOException
     */
    @Override
    public Need CreateNeed(Need need) throws IOException {
        // TODO Auto-generated method stub
        if(needs.containsKey(need.getId())){
            return null; // if the need exists, do nothing.
        } else {
            this.needs.put(need.getId(), need);
            // might need to save new need
            return need;
        }
    }

    /**
     * UpdateNeed - If the need parameter's ID exists, the need is replaced.
     * @param need - This is the need being updated.
     * @return - Parameter need if successful, null if not.
     * @throws IOException
     */
    @Override
    public Need UpdateNeed(Need need) throws IOException {
        // TODO Auto-generated method stub
        if(this.needs.containsKey(need.getId())) {
            this.needs.put(need.getId(), need);
            return need;
        } else {
            return null;
        }
    }

    /**
     * GetNeed - Returns the need if it exists.
     * @param id - Tnput need ID
     * @return Either found need, or null.
     * @throws IOException
     */
    @Override
    public Need GetNeed(int id) throws IOException {
        // TODO Auto-generated method stub
            if (this.needs.containsKey(id)){
                return this.needs.get(id);
            }
            else{
                return null;
            }
    }

    @Override
    public Need[] GetNeeds() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Need[] SearchNeeds(String substring) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DeleteNeed - Deletes the need if it's ID exists.
     * @param id - ID of need looking to be remove.
     * @return - True if need exists, false if it does not.
     * @throws IOException
     */
    @Override
    public boolean DeleteNeed(int id) throws IOException {
        // TODO Auto-generated method stub
        if (this.needs.containsKey(id)){
            this.needs.remove(id);
        }
        else{
            return false;
        }
    }
}
