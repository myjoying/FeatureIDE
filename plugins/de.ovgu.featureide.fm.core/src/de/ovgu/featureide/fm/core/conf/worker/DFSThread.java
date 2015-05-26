/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2015  FeatureIDE team, University of Magdeburg, Germany
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
package de.ovgu.featureide.fm.core.conf.worker;

import java.util.Arrays;

import de.ovgu.featureide.fm.core.conf.FeatureGraph;
import de.ovgu.featureide.fm.core.conf.worker.base.AWorkerThread;
import de.ovgu.featureide.fm.core.job.WorkMonitor;

public class DFSThread extends AWorkerThread<String> {

	private static class SharedObjects {
		private final FeatureGraph featureGraph;

		public SharedObjects(FeatureGraph featureGraph) {
			this.featureGraph = featureGraph;
		}
	}

	private final byte[] visited;
	private final SharedObjects sharedObjects;

	public DFSThread(FeatureGraph featureGraph, WorkMonitor workMonitor) {
		super(workMonitor);
		sharedObjects = new SharedObjects(featureGraph);
		visited = new byte[featureGraph.featureArray.length];
	}

	private DFSThread(DFSThread oldThread) {
		super(oldThread);
		this.sharedObjects = oldThread.sharedObjects;
		visited = new byte[oldThread.sharedObjects.featureGraph.featureArray.length];
	}

	@Override
	protected void work(String object) {
		final int featureIndex = sharedObjects.featureGraph.getFeatureIndex(object);
		Arrays.fill(visited, (byte) 0);
		sharedObjects.featureGraph.dfs(visited, featureIndex, true);
		Arrays.fill(visited, (byte) 0);
		sharedObjects.featureGraph.dfs(visited, featureIndex, false);
	}

	@Override
	protected DFSThread newThread() {
		return new DFSThread(this);
	}

}
