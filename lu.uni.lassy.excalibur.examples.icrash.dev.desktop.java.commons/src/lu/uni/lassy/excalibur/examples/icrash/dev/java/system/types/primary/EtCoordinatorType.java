/**
 * 
 */
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * @author alexb
 *
 */

/**
 * The Enum EtCoordinatorType, which dictates the type of coordinator, 
 * which could be the hospital, government service, private company etc. 
 * For example, hospital could manage some crises, which correspond to the injury of a victim.
 */
public enum EtCoordinatorType implements JIntIs  {
	/** A normal coordinator. */
	normal, 
	/** A hospital. */
	hospital;
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#is()
	 */
	public PtBoolean is(){
		return new PtBoolean(this.name() == EtCoordinatorType.normal.name() ||
				this.name() == EtCoordinatorType.hospital.name());
	}
}
