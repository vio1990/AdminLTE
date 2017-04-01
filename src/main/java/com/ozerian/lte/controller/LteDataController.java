package com.ozerian.lte.controller;

import com.ozerian.lte.model.LteData;
import com.ozerian.lte.service.CrudLteDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * LteDataController is for processing requests regarding
 * addition, removal, update and get operations for LteData
 * entity.
 */
@RestController
@RequestMapping(value = "/tables")
public class LteDataController {

    private final Logger logger = LoggerFactory.getLogger(LteDataController.class);

    @Inject
    private CrudLteDataService lteDataService;

    /**
     * Retrieve the list of LteData entities inside of ResponseEntity with Http status OK.
     *
     * @return ResponseEntity with Http status OK and list of LteData in JSON format.
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllLteData() {
        logger.debug("Execution of getLteData");

        List<LteData> lteDataList = lteDataService.getAllLteData();

        if (lteDataList == null) {
            logger.error("Got lte data list is null!");
            return ResponseEntity.notFound().build();
        }

        logger.debug("Lte date list size is '{}'", lteDataList);

        return ResponseEntity.ok(lteDataList);
    }

    /**
     * Retrieve the LteData entity by it's id inside of ResponseEntity with Http status OK.
     *
     * @param id Long id of specific LteData for getting.
     * @return ResponseEntity with Http status OK and LteData in JSON format.
     */
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getLteDataById(@PathVariable("id") Long id) {
        logger.debug("Execution of getLteDataById");

        LteData gotLteData = lteDataService.getLteDataById(id);

        if (gotLteData == null) {
            logger.error("Got lte data entity is null!");
            return ResponseEntity.notFound().build();
        }

        logger.debug("Got lte data is '{}'", gotLteData.toString());

        return ResponseEntity.ok(gotLteData);
    }

    /**
     * Remove the LteData entity by it's id and return ResponseEntity with status OK.
     *
     * @param id Long id of LteData for removal.
     * @return ResponseEntity http status OK after removal.
     */
    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> removeLteDataById(@PathVariable("id") Long id) {
        logger.debug("Execution of removeLteDataById");

        lteDataService.deleteLteDataById(id);

        logger.debug("Chosen LteData has been removed!");

        return ResponseEntity.ok().build();
    }

    /**
     * Add new LteData entity and get it inside of ResponseEntity with Http status OK
     *
     * @param data LteData got from Request body LteData entity for addition.
     * @return ResponseEntity with http status OK and added LteData in JSON format.
     */
    @RequestMapping(value = "/data/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addLteData(@RequestBody LteData data) {
        logger.debug("Execution of addLteData");

        if (data == null) {
            logger.debug("Request body entity is null!");
            return ResponseEntity.badRequest().build();
        }

        LteData savedLteData = lteDataService.saveLteData(data);

        logger.debug("Saved LteData is '{}'", savedLteData.toString());

        return ResponseEntity.ok(savedLteData);
    }

    /**
     * Update existing LteData entity and get it inside of ResponseEntity with Http status OK
     *
     * @param data LteData got from Request body LteData entity for updating.
     * @return ResponseEntity with http status OK and updated LteData in JSON format.
     */
    @RequestMapping(value = "/data/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateLteData(@RequestBody LteData data) {
        logger.debug("Execution of updateLteData");

        if (data == null) {
            logger.debug("Request body entity is null!");
            return ResponseEntity.badRequest().build();
        }

        LteData updatedLteData = lteDataService.saveLteData(data);

        logger.debug("Updated LteData is '{}'", updatedLteData.toString());

        return ResponseEntity.ok(updatedLteData);
    }

    /**
     * Retrieve the list of LteData entities ordered by Rendering engine inside of ResponseEntity
     * with Http status OK.
     *
     * @return ResponseEntity with Http status OK and ordered list of LteData in JSON format.
     */
    @RequestMapping(value = "/data/ordered", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOrderedByRenEngineLteData() {
        logger.debug("Execution of getOrderedByRenEngineLteData");

        List<LteData> orderedLteDataList = lteDataService.getAllLteDataGroupedByRenderingName();

        if (orderedLteDataList == null) {
            logger.error("Ordered lte data list is null!");
            return ResponseEntity.notFound().build();
        }

        logger.debug("Ordered LteData lists's size is '{}'", orderedLteDataList.size());

        return ResponseEntity.ok(orderedLteDataList);
    }
}
