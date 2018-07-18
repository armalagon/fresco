package com.fresco.presentation.parameter;

import com.fresco.business.parameter.logic.ParameterProvider;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.presentation.ScreenMode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Named
@ViewScoped
public class ParameterBean implements Serializable {

    @Inject
    ParameterProvider provider;

    private String criteria;
    private List<Parameter> results;
    private Parameter selected;

    private String emptyMessage;
    private ScreenMode mode;

    @PostConstruct
    public void init() {
        results = new ArrayList<>();
        emptyMessage = "&nbsp;";
        mode = ScreenMode.SEARCH;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public List<Parameter> getResults() {
        return results;
    }

    public void setResults(List<Parameter> results) {
        this.results = results;
    }

    public Parameter getSelected() {
        return selected;
    }

    public void setSelected(Parameter selected) {
        this.selected = selected;
        this.mode = ScreenMode.UPDATE;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public ScreenMode getMode() {
        return mode;
    }

    public void setMode(ScreenMode mode) {
        this.mode = mode;
    }

    public boolean isOnSearchingMode() {
        return mode == ScreenMode.SEARCH;
    }

    public boolean isOnEditionMode() {
        return mode == ScreenMode.UPDATE;
    }

    public void search() {
        emptyMessage = "No records found!";
        results = provider.findByText(criteria);
    }

    public void cancel() {
        mode = ScreenMode.SEARCH;
    }

}
