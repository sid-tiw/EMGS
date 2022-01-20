package com.paytmbank.middleware.emgs.service;

import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.entity.Project;
import com.paytmbank.middleware.emgs.exception.*;
import com.paytmbank.middleware.emgs.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ProjectService {
    @Autowired
    ProjectRepository projectRepo;

    public void save(Project prj) {
        projectRepo.save(prj);
    }

    public Page<Project> listAll() {
        return projectRepo.findAll(Pageable.ofSize(10));
    }

    public Project find(String pid) throws ProjectNotFound {
        if (!projectRepo.existsById(pid)) throw new ProjectNotFound("No such project ID.");
        Project prj = projectRepo.getById(pid);
        return prj;
    }

    /* To create new employee. An employee object is passed, and then checks are done inside this function, before ultimately calling
    the save function inside here. */
    public void create(Project prj) throws Exception {
        /* Checks start here */

        // If already found, then throw an error.
        if (projectRepo.existsById(prj.getPid())) throw new ProjectAlreadyPresent("Error!! Another project with same id exists.");

        /* Checks end here */

        this.save(prj);
    }

    /* Delete Project function. First it finds which Project to delete
    and if not found, then ProjectNotFound error is returned, otherwise the
    Project is deleted. Deleting project, might have grave consequences on other
    records linked with project. This must be checked.
     */
    public void delete(String pid) throws ProjectNotFound {
        if (!projectRepo.existsById(pid)) throw new ProjectNotFound("No such project ID.");
        projectRepo.delete(projectRepo.getById(pid));
    }

    /* Update Project function. Anything can be updated, except the id.
     */
    public void update(Project prj) throws Exception {
        if (!projectRepo.existsById(prj.getPid())) throw new ProjectNotFound("No such project ID.");
        projectRepo.delete(projectRepo.getById(prj.getPid()));
        this.create(prj);
    }
}
