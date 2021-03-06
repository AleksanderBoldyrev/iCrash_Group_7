/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Class CtCoordinator which extends the class of CtAuthenticated
 * This is the class that stores details about the coordinator.
 */
public class CtCoordinator extends CtAuthenticated {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The id of the coordinator. */
	public DtCoordinatorID id;
	
	/** The type of person the coordinators are. */
	public EtCoordinatorType type; // NEW CODE!!!!!!!
	
	/**
	 * Initialises the coordinator.
	 *
	 * @param aId The ID of the coordinator
	 * @param aLogin The username of the coordinator
	 * @param aPwd The password of the coordinator
	 * @param aType The type of the coordinator
	 * @return The success of the initialisation
	 */
	public PtBoolean init(DtCoordinatorID aId,DtLogin aLogin,DtPassword aPwd, EtCoordinatorType aType){
			super.init(aLogin, aPwd);
			id = aId;
			type = aType; // NEW CODE
			return new PtBoolean(true); 
	}
	
	/**
	 * Updates the user details, used when the administrator does an update on the coordinator.
	 *
	 * @param aLogin The value to change the login to
	 * @param aPwd the value to change the password to 
	 * @param aType The type of the coordinator
	 * @return the success of the update method
	 */
	public PtBoolean update(DtLogin aLogin,DtPassword aPwd, EtCoordinatorType aType){
		login = aLogin;
		pwd = aPwd;
		type = aType; // NEW CODE
		return new PtBoolean(true);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CtCoordinator))
			return false;
		CtCoordinator aCtCoordinator = (CtCoordinator)obj;
		if (!aCtCoordinator.id.value.getValue().equals(this.id.value.getValue()))
			return false;
		if (!aCtCoordinator.type.equals(this.type)) // NEW CODE
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.id.value.getValue().length() + super.hashCode();
	}
}
