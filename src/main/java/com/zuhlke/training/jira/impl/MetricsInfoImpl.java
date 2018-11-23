package com.zuhlke.training.jira.impl;

import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserProjectHistoryManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import java.util.HashMap;
import java.util.Map;

import static com.atlassian.jira.security.Permissions.PROJECT_ADMIN;

@Scanned
public class MetricsInfoImpl extends AbstractJiraContextProvider implements com.zuhlke.training.jira.api.MetricsInfo {

    @ComponentImport
    private final UserProjectHistoryManager userProjectHistoryManager;

    public MetricsInfoImpl(UserProjectHistoryManager userProjectHistoryManager){
        this.userProjectHistoryManager = userProjectHistoryManager;
    }

    @Override
    public Map getContextMap(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        Map<String, Object> contextMap = new HashMap<>();

        Project currentProject = userProjectHistoryManager.getCurrentProject(PROJECT_ADMIN, applicationUser);
        if(null != currentProject) {
            contextMap.put(PROJECT, currentProject);
        }

        return contextMap;
    }
}
