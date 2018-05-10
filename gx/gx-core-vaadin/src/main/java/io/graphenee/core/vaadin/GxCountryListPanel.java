/*******************************************************************************
 * Copyright (c) 2016, 2017, Graphenee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package io.graphenee.core.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.graphenee.core.model.api.GxDataService;
import com.graphenee.core.model.bean.GxCountryBean;
import com.graphenee.vaadin.AbstractEntityListPanel;
import com.graphenee.vaadin.TRAbstractForm;
import com.vaadin.spring.annotation.SpringComponent;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxCountryListPanel extends AbstractEntityListPanel<GxCountryBean> {

	@Autowired
	GxDataService dataService;

	@Autowired
	GxCountryForm editorForm;

	public GxCountryListPanel() {
		super(GxCountryBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxCountryBean entity) {
		dataService.createOrUpdate(entity);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(GxCountryBean entity) {
		dataService.delete(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return "Countries";
	}

	@Override
	protected List<GxCountryBean> fetchEntities() {
		return dataService.findCountry();
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "countryName", "alpha3Code", "numericCode" };
	}

	@Override
	protected TRAbstractForm<GxCountryBean> editorForm() {
		return editorForm;
	}

	@Override
	protected boolean isGridCellFilterEnabled() {
		return true;
	}
}