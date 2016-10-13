/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2016  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.ui.views.configMap.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import de.ovgu.featureide.ui.views.configMap.IConfigurationMapFilter;
import de.ovgu.featureide.ui.views.configMap.IConfigurationMapFilterable;

/**
 * TODO description
 * 
 * @author gruppe40
 */
public class ConfigMapFilterAction extends Action {
	private IConfigurationMapFilter filter;
	private IConfigurationMapFilterable filterable;
	
	public ConfigMapFilterAction(IConfigurationMapFilter filter, IConfigurationMapFilterable filterable) {
		super(filter.getName(), Action.AS_CHECK_BOX);
		this.filter = filter;
		this.filterable = filterable;
		setChecked(filter.isDefault());
	}
	
	public void initializeImage(Image image) {
		setImageDescriptor(ImageDescriptor.createFromImage(image));
	}
	
	public IConfigurationMapFilter getFilter() {
		return filter;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#setChecked(boolean)
	 */
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		if (checked)
			this.filterable.addFilter(this.filter);
		else
			this.filterable.removeFilter(this.filter);
	}

}
