package com.fresco.presentation.parameter;

import com.fresco.business.parameter.logic.ParameterProvider;
import com.fresco.business.parameter.model.Parameter;
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
public class ParameterView implements Serializable {

    private String criteria;
    private List<Parameter> results;
    private Parameter selected;

    private String emptyMessage;

    @Inject
    ParameterProvider provider;

    @PostConstruct
    public void init() {
        results = new ArrayList<>();
        emptyMessage = "&nbsp;";
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
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public void search() {
        emptyMessage = "No records found!";
        results = provider.findByText(criteria);
    }

}
