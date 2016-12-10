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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.CoordinatorController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCoordinatorType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The Class MainICrashGUI that allows creation of a coordinator iCrash window.
 */
public class CreateICrashCoordGUI implements CreatedWindows {
	
	/**
	 * Instantiates a new window for the coordinator to use iCrash.
	 *
	 * @param aDtCoordinatorID the ID of the coordinator associated with this window
	 * @param aActCoordinator the actor associated with this window
	 */
	public CreateICrashCoordGUI(DtCoordinatorID aDtCoordinatorID, ActCoordinator aActCoordinator) {
		this._aDtCoordinatorID = aDtCoordinatorID;
		start(aActCoordinator);
	}
	
	// new code
	public CreateICrashCoordGUI(SystemStateController sysc) {
		sysController = sysc;
		start();
	}
	
	public void setSysController(SystemStateController sysc)
	{
		sysController = sysc;
		start();
		ArrayList<CtCoordinator> coords;
		try {
			coords = sysController.getAllCoordinators();
			if (coords.size()>0) {
				for (int i=0; i<coords.size(); i++)	{
					if (coords.get(i).id.value.getValue().compareTo(_aDtCoordinatorID.value.getValue())==0) {
						type = coords.get(i).type;
						break;
					}
				}
			}
		} catch (ServerOfflineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerNotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	SystemStateController sysController;
	
	private EtCoordinatorType type; // new code
	
	/** The Coordinator ID that was used to create this window, it's used to work out which windows to close when a coordinator has been deleted. */
	private DtCoordinatorID _aDtCoordinatorID;	
	
	/**
	 * Gets the data type of the coordinator's ID.
	 *
	 * @return the datatype of the coordinator's ID
	 */
	public DtCoordinatorID getDtCoordinatorID(){
		return this._aDtCoordinatorID;
	}
	
	/** The stage that the form will be held in. */
	private Stage stage;
	
	/**
	 * Starts the system and opens the window up (If no exceptions have been thrown).
	 *
	 * @param aActCoordinator the actor associated with this window
	 */
	private void start() { // новый код
		try {
			URL url = this.getClass().getResource("ICrashCoordGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Coordinator");
            stage.setScene(new Scene(root));
            stage.show();
            ((ICrashCoordGUIController)loader.getController()).setGUI(this);
          //  ((ICrashCoordGUIController)loader.getController()).setActor(_aDtCoordinatorID);
            ((ICrashCoordGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((ICrashCoordGUIController)loader.getController()).closeForm();
				}
			});
		} catch(Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}
	
	/**
	 * Allows the other screens to force this window to close. Will be used if the coordinator has been deleted
	 */
	
	/**
	 * Starts the system and opens the window up (If no exceptions have been thrown).
	 *
	 * @param aActCoordinator the actor associated with this window
	 */
	private void start(ActCoordinator aActCoordinator) {
		try {
			URL url = this.getClass().getResource("ICrashCoordGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Coordinator - " + aActCoordinator.getLogin().value.getValue());
            stage.setScene(new Scene(root));
            stage.show();
            ((ICrashCoordGUIController)loader.getController()).setActor(aActCoordinator);
            ((ICrashCoordGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((ICrashCoordGUIController)loader.getController()).closeForm();
				}
			});
		} catch(Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}
	
	/**
	 * Allows the other screens to force this window to close. Will be used if the coordinator has been deleted
	 */
	@Override
	public void closeWindow(){
		if (stage != null)
			stage.close();
	}
	
	// new code
	public ActCoordinator getContrl(String login)
	{
		try {
			ActCoordinator ac = sysController.getActCoordinator(login);
			ArrayList<CtCoordinator> coords =  sysController.getAllCoordinators();
			if (coords.size()>0) {
				for (int i=0; i<coords.size(); i++)	{
					if (coords.get(i).login.value.getValue().compareTo(login)==0) {
						type = coords.get(i).type;
						break;
					}
				}
			}
			return ac;//sysController.getActCoordinator(login));//ac)
		} catch (ServerNotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerOfflineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public EtCoordinatorType getCoordType()
	{
		if (type == EtCoordinatorType.hospital)
			stage.setTitle("iCrash Coordinator - hospital");
		return type;

	}
	
	
}
