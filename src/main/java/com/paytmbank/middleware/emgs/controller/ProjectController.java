package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.ProjectDetailsBasic;
import com.paytmbank.middleware.emgs.entity.Project;
import com.paytmbank.middleware.emgs.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    /* Get the list of all projects. This controller method is strictly for testing purposes. */
    /* Although this will be available to the super_admin during production (if any such thing happens). */
    @GetMapping("/projects")
    public ResponseEntity<?> getProjects() {
        Page<Project> pg = projectService.listAll();
        return ResponseEntity.ok().body(pg);
    }

    /* As the name suggests, it will create a new employee, with validity tests that will take place in the service class */
    @PostMapping("/createProject")
    public ResponseEntity<?> createNewProject(@RequestBody Project prj) {
        /* Basic returnable object, detailing the error and status. */
        ProjectDetailsBasic obj = new ProjectDetailsBasic();
        try {
            projectService.create(prj);
        } catch (Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
        /* If the check is successful then return the basic details of the Project */
        obj.setPrj(prj);

        return ResponseEntity.ok().body(obj);
    }

    /* Get Project by its id. The id parameter maps to eid. */
    @GetMapping("/getProject/{pid}")
    public ResponseEntity<?> getProject(@PathVariable(name = "pid", required = true) String pid) {
        ProjectDetailsBasic obj = new ProjectDetailsBasic();
        try {
            Project prj = projectService.find(pid);
            return ResponseEntity.ok().body(prj);
        } catch(Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* The delete controller method */
    @PostMapping("/deleteProject/{pid}")
    public ResponseEntity<?> delete(@PathVariable(name = "pid", required = true) String pid) {
        ProjectDetailsBasic obj = new ProjectDetailsBasic();
        try {
            projectService.delete(pid);
            return ResponseEntity.ok().body("Delete successful.");
        } catch (Exception e) {
            obj.setStatus("Failure");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Update the Project */
    @PostMapping("/updateProject")
    public ResponseEntity<?> update(@RequestBody Project prj) {
        ProjectDetailsBasic obj = new ProjectDetailsBasic();
        try {
            projectService.update(prj);
            obj.setPrj(prj);
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }
}
