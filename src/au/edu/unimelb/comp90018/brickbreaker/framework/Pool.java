package au.edu.unimelb.comp90018.brickbreaker.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Oscar
 * 
 *         The Android input system fires many of KeyEvent and TouchEvent events
 *         when a key is pressed or a finger touches the screen, so we
 *         constantly create new instances that are collected by the garbage
 *         collector in short intervals. In order to avoid this, we implement a
 *         concept known as instance pooling. Instead of repeatedly creating new
 *         instances of a class, we simply reuse previously created instances.
 *         Please note that you must be careful to fully reinitialize reused
 *         objects when they’re fetched from the Pool.
 * 
 * @param <T>
 */
public class Pool<T> {

	public interface PoolObjectFactory<T> {
		public T createObject();
	}

	private final List<T> freeObjects;
	private final PoolObjectFactory<T> factory;
	private final int maxSize;

	public Pool(PoolObjectFactory<T> factory, int maxSize) {
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>(maxSize);
	}

	public T newObject() {
		T object = null;
		if (freeObjects.isEmpty())
			object = factory.createObject();
		else
			object = freeObjects.remove(freeObjects.size() - 1);

		return object;
	}

	public void free(T object) {
		if (freeObjects.size() < maxSize)
			freeObjects.add(object);
	}
}
