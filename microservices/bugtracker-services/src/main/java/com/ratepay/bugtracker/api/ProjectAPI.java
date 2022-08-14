package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.services.ProjectService;
import com.ratepay.client.bugtracker.models.ProjectModel;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/project")
public class ProjectAPI extends BaseRestSqlModeImpl<ProjectModel, Long> {
    public ProjectAPI(ProjectService projectService){
        super(projectService);
    }
}
