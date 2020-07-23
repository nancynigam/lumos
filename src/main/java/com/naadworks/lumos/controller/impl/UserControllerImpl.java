package com.naadworks.lumos.controller.impl;

import com.naadworks.lumos.entry.UserEntry;
import com.naadworks.lumos.exception.BaseException;
import com.naadworks.lumos.service.impl.UserServiceImpl;
import com.naadworks.lumos.util.Status;
import com.naadworks.lumos.util.StatusType;
import com.naadworks.lumos.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerImpl {

    private static final Logger log = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private UserServiceImpl userService;

    //TODO : how to check for null value?
    //TODO : Since my db is an interface I have extended DAOException to Runtime exception.. Is that ok?
    // Why are we even catching runtime exception shouldn't we just do a null check before the DB call?
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    public UserView getById(@PathVariable("id") Long id) {
        Status status = null;
        List<UserEntry> data = new ArrayList<>();
        try {
            UserEntry entryFetched = getUserService().findById(id);
            data.add(entryFetched);
            log.info("UserEntry with id = " + id + " found successfully");
            status = new Status(StatusType.SUCCESS);
        } catch (BaseException be) {
            log.error("Error during find by id = " + be + " ");
            be.printStackTrace();
            status = new Status(StatusType.ERROR);
            status.setMessage(status.getMessage() + ". " + be.getMessage());
        }
        return new UserView(status, data);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    public UserView getAll() {
        Status status = null;
        List<UserEntry> data = new ArrayList<>();
        data = getUserService().findAll();
        status = new Status(StatusType.SUCCESS);
        return new UserView(status, data);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public UserView create(@RequestBody UserEntry userEntry) {
        Status status = null;
        List<UserEntry> data = new ArrayList<>();
        try {
            UserEntry entryCreated = getUserService().create(userEntry);
            data.add(entryCreated);
            log.info("UserEntry with id = " + entryCreated.getId() + " created successfully");
            status = new Status(StatusType.SUCCESS);
        } catch (BaseException be) {
            log.error("ERROR during creating a new entry = " + be + " ");
            be.printStackTrace();
            status = new Status(StatusType.ERROR);
            status.setMessage(status.getMessage() + ". " + be.getMessage());
        }
        return new UserView(status, data);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    public UserView update(@RequestBody UserEntry userEntry, @PathVariable("id") Long id) {
        Status status = null;
        List<UserEntry> data = new ArrayList<>();
        try {
            UserEntry entryUpdated = getUserService().update(userEntry, id);
            data.add(entryUpdated);
            log.info("UserEntry with id = " + entryUpdated.getId() + " updated successfully");
            status = new Status(StatusType.SUCCESS);
        } catch (BaseException be) {
            log.error("ERROR during updating entry = " + be + " ");
            be.printStackTrace();
            status = new Status(StatusType.ERROR);
            status.setMessage(status.getMessage() + ". " + be.getMessage());
        }
        return new UserView(status, data);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public UserView delete(@PathVariable("id") Long id) {
        Status status = null;
        List<UserEntry> data = new ArrayList<>();
        try {
            getUserService().delete(id);
            log.info("UserEntry with id = " + id + " deleted successfully");
            status = new Status(StatusType.SUCCESS);
        } catch (BaseException be) {
            log.error("ERROR during deleting entry = " + be + " ");
            be.printStackTrace();
            status = new Status(StatusType.ERROR);
            status.setMessage(status.getMessage() + ". " + be.getMessage());
        }

        return new UserView(status, data);
    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
}
