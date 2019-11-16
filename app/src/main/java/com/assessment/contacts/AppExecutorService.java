package com.assessment.contacts;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class can be used to perform any arbitrary task on a separate thread. and also provides the MainThread executor.
 **/
public class AppExecutorService {

	//Thread limit for background execution of arbitrary task.
	private static final int BACKGROUND_THREAD_COUNT = 2;

	//Background executor service to perform intensive/long running operation.
	private Executor mBackgroundExecutor;

	//MainThreadExecutor used to perform task on MainThread.
	private Executor mMainThreadExecutor;

	//Singleton instance.
	private static AppExecutorService sInstance;

	public static AppExecutorService getInstance() {
		if (null == sInstance) {
			synchronized (AppExecutorService.class) {
				sInstance = new AppExecutorService();
			}
		}
		return sInstance;
	}

	/**
	 * Use {@link #getInstance()} to obtain the instance.
	 */
	private AppExecutorService() {
		//Singleton class, object creation outside this class, is forbidden.
		mBackgroundExecutor = Executors.newFixedThreadPool(BACKGROUND_THREAD_COUNT);
		mMainThreadExecutor = new MainThreadExecutor();
	}

	/**
	 * @return The background thread executor, can used to perform any intensive/long-running task.
	 */
	public Executor getBackgroundThreadExecutor() {
		return mBackgroundExecutor;
	}

	/**
	 * @return The MainThread Executor used to run jobs on MainThread from any context.
	 */
	public Executor getMainThreadExecutor() {
		return mMainThreadExecutor;
	}

	/**
	 * Custom implementation of Executor for executing task on MainThread.
	 */
	private static class MainThreadExecutor implements Executor {
		private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

		@Override
		public void execute(@NonNull Runnable command) {
			mMainThreadHandler.post(command);
		}
	}
}
