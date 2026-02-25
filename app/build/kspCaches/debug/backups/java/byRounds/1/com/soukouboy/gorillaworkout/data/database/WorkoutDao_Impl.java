package com.soukouboy.gorillaworkout.data.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.soukouboy.gorillaworkout.data.model.Workout;
import com.soukouboy.gorillaworkout.data.model.WorkoutExercise;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkoutDao_Impl implements WorkoutDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Workout> __insertionAdapterOfWorkout;

  private final EntityInsertionAdapter<WorkoutExercise> __insertionAdapterOfWorkoutExercise;

  private final EntityDeletionOrUpdateAdapter<Workout> __deletionAdapterOfWorkout;

  private final EntityDeletionOrUpdateAdapter<WorkoutExercise> __deletionAdapterOfWorkoutExercise;

  private final EntityDeletionOrUpdateAdapter<Workout> __updateAdapterOfWorkout;

  private final EntityDeletionOrUpdateAdapter<WorkoutExercise> __updateAdapterOfWorkoutExercise;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllWorkoutExercises;

  public WorkoutDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkout = new EntityInsertionAdapter<Workout>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `workouts` (`id`,`name`,`date`,`duration`,`totalCalories`,`notes`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Workout entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getDate());
        statement.bindLong(4, entity.getDuration());
        statement.bindLong(5, entity.getTotalCalories());
        statement.bindString(6, entity.getNotes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__insertionAdapterOfWorkoutExercise = new EntityInsertionAdapter<WorkoutExercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workout_exercises` (`workoutId`,`exerciseId`,`sets`,`reps`,`weight`,`restTime`,`order`,`isCompleted`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutExercise entity) {
        statement.bindLong(1, entity.getWorkoutId());
        statement.bindLong(2, entity.getExerciseId());
        statement.bindLong(3, entity.getSets());
        statement.bindLong(4, entity.getReps());
        statement.bindDouble(5, entity.getWeight());
        statement.bindLong(6, entity.getRestTime());
        statement.bindLong(7, entity.getOrder());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__deletionAdapterOfWorkout = new EntityDeletionOrUpdateAdapter<Workout>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workouts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Workout entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfWorkoutExercise = new EntityDeletionOrUpdateAdapter<WorkoutExercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workout_exercises` WHERE `workoutId` = ? AND `exerciseId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutExercise entity) {
        statement.bindLong(1, entity.getWorkoutId());
        statement.bindLong(2, entity.getExerciseId());
      }
    };
    this.__updateAdapterOfWorkout = new EntityDeletionOrUpdateAdapter<Workout>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workouts` SET `id` = ?,`name` = ?,`date` = ?,`duration` = ?,`totalCalories` = ?,`notes` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Workout entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getDate());
        statement.bindLong(4, entity.getDuration());
        statement.bindLong(5, entity.getTotalCalories());
        statement.bindString(6, entity.getNotes());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getId());
      }
    };
    this.__updateAdapterOfWorkoutExercise = new EntityDeletionOrUpdateAdapter<WorkoutExercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workout_exercises` SET `workoutId` = ?,`exerciseId` = ?,`sets` = ?,`reps` = ?,`weight` = ?,`restTime` = ?,`order` = ?,`isCompleted` = ? WHERE `workoutId` = ? AND `exerciseId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutExercise entity) {
        statement.bindLong(1, entity.getWorkoutId());
        statement.bindLong(2, entity.getExerciseId());
        statement.bindLong(3, entity.getSets());
        statement.bindLong(4, entity.getReps());
        statement.bindDouble(5, entity.getWeight());
        statement.bindLong(6, entity.getRestTime());
        statement.bindLong(7, entity.getOrder());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getWorkoutId());
        statement.bindLong(10, entity.getExerciseId());
      }
    };
    this.__preparedStmtOfDeleteAllWorkoutExercises = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM workout_exercises WHERE workoutId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWorkout(final Workout workout, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkout.insertAndReturnId(workout);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertWorkoutExercise(final WorkoutExercise workoutExercise,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWorkoutExercise.insert(workoutExercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkout(final Workout workout, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkout.handle(workout);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkoutExercise(final WorkoutExercise workoutExercise,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutExercise.handle(workoutExercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorkout(final Workout workout, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkout.handle(workout);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorkoutExercise(final WorkoutExercise workoutExercise,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutExercise.handle(workoutExercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllWorkoutExercises(final int workoutId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllWorkoutExercises.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, workoutId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllWorkoutExercises.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Workout>> getAllWorkouts() {
    final String _sql = "SELECT * FROM workouts ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<List<Workout>>() {
      @Override
      @Nullable
      public List<Workout> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfTotalCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCalories");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workout _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final int _tmpTotalCalories;
            _tmpTotalCalories = _cursor.getInt(_cursorIndexOfTotalCalories);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new Workout(_tmpId,_tmpName,_tmpDate,_tmpDuration,_tmpTotalCalories,_tmpNotes,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Workout>> getCompletedWorkouts() {
    final String _sql = "SELECT * FROM workouts WHERE isCompleted = 1 ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<List<Workout>>() {
      @Override
      @Nullable
      public List<Workout> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfTotalCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCalories");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workout _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final int _tmpTotalCalories;
            _tmpTotalCalories = _cursor.getInt(_cursorIndexOfTotalCalories);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new Workout(_tmpId,_tmpName,_tmpDate,_tmpDuration,_tmpTotalCalories,_tmpNotes,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Workout>> getRecentWorkouts() {
    final String _sql = "SELECT * FROM workouts WHERE isCompleted = 1 ORDER BY date DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<List<Workout>>() {
      @Override
      @Nullable
      public List<Workout> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfTotalCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCalories");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workout _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final int _tmpTotalCalories;
            _tmpTotalCalories = _cursor.getInt(_cursorIndexOfTotalCalories);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new Workout(_tmpId,_tmpName,_tmpDate,_tmpDuration,_tmpTotalCalories,_tmpNotes,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWorkoutById(final int workoutId,
      final Continuation<? super Workout> $completion) {
    final String _sql = "SELECT * FROM workouts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workoutId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Workout>() {
      @Override
      @Nullable
      public Workout call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfTotalCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCalories");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final Workout _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final int _tmpTotalCalories;
            _tmpTotalCalories = _cursor.getInt(_cursorIndexOfTotalCalories);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _result = new Workout(_tmpId,_tmpName,_tmpDate,_tmpDuration,_tmpTotalCalories,_tmpNotes,_tmpIsCompleted);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getWorkoutExercisesByWorkoutId(final int workoutId,
      final Continuation<? super List<WorkoutExercise>> $completion) {
    final String _sql = "SELECT * FROM workout_exercises WHERE workoutId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workoutId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<WorkoutExercise>>() {
      @Override
      @NonNull
      public List<WorkoutExercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfRestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "restTime");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<WorkoutExercise> _result = new ArrayList<WorkoutExercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutExercise _item;
            final int _tmpWorkoutId;
            _tmpWorkoutId = _cursor.getInt(_cursorIndexOfWorkoutId);
            final int _tmpExerciseId;
            _tmpExerciseId = _cursor.getInt(_cursorIndexOfExerciseId);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            final int _tmpRestTime;
            _tmpRestTime = _cursor.getInt(_cursorIndexOfRestTime);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new WorkoutExercise(_tmpWorkoutId,_tmpExerciseId,_tmpSets,_tmpReps,_tmpWeight,_tmpRestTime,_tmpOrder,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Workout>> getWorkoutsSince(final long startDate) {
    final String _sql = "SELECT * FROM workouts WHERE isCompleted = 1 AND date >= ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<List<Workout>>() {
      @Override
      @Nullable
      public List<Workout> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfTotalCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCalories");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workout _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final int _tmpTotalCalories;
            _tmpTotalCalories = _cursor.getInt(_cursorIndexOfTotalCalories);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new Workout(_tmpId,_tmpName,_tmpDate,_tmpDuration,_tmpTotalCalories,_tmpNotes,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Workout>> getWorkoutsBetween(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM workouts WHERE isCompleted = 1 AND date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<List<Workout>>() {
      @Override
      @Nullable
      public List<Workout> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfTotalCalories = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCalories");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workout _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final int _tmpTotalCalories;
            _tmpTotalCalories = _cursor.getInt(_cursorIndexOfTotalCalories);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new Workout(_tmpId,_tmpName,_tmpDate,_tmpDuration,_tmpTotalCalories,_tmpNotes,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalWorkoutTime() {
    final String _sql = "SELECT SUM(duration) FROM workouts WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalCompletedWorkouts() {
    final String _sql = "SELECT COUNT(*) FROM workouts WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalWorkoutTimes() {
    final String _sql = "SELECT SUM(duration) FROM workouts WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalCaloriesBurned() {
    final String _sql = "SELECT SUM(totalCalories) FROM workouts WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getAverageWorkoutDuration() {
    final String _sql = "SELECT AVG(duration) FROM workouts WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getLongestWorkoutDuration() {
    final String _sql = "SELECT MAX(duration) FROM workouts WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workouts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<WorkoutExercise>> getWorkoutExercises(final int workoutId) {
    final String _sql = "SELECT * FROM workout_exercises WHERE workoutId = ? ORDER BY `order` ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workoutId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"workout_exercises"}, false, new Callable<List<WorkoutExercise>>() {
      @Override
      @Nullable
      public List<WorkoutExercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfRestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "restTime");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final List<WorkoutExercise> _result = new ArrayList<WorkoutExercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutExercise _item;
            final int _tmpWorkoutId;
            _tmpWorkoutId = _cursor.getInt(_cursorIndexOfWorkoutId);
            final int _tmpExerciseId;
            _tmpExerciseId = _cursor.getInt(_cursorIndexOfExerciseId);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            final int _tmpRestTime;
            _tmpRestTime = _cursor.getInt(_cursorIndexOfRestTime);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item = new WorkoutExercise(_tmpWorkoutId,_tmpExerciseId,_tmpSets,_tmpReps,_tmpWeight,_tmpRestTime,_tmpOrder,_tmpIsCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWorkoutExercise(final int workoutId, final int exerciseId,
      final Continuation<? super WorkoutExercise> $completion) {
    final String _sql = "SELECT * FROM workout_exercises WHERE workoutId = ? AND exerciseId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workoutId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, exerciseId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorkoutExercise>() {
      @Override
      @Nullable
      public WorkoutExercise call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseId");
          final int _cursorIndexOfSets = CursorUtil.getColumnIndexOrThrow(_cursor, "sets");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfRestTime = CursorUtil.getColumnIndexOrThrow(_cursor, "restTime");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final WorkoutExercise _result;
          if (_cursor.moveToFirst()) {
            final int _tmpWorkoutId;
            _tmpWorkoutId = _cursor.getInt(_cursorIndexOfWorkoutId);
            final int _tmpExerciseId;
            _tmpExerciseId = _cursor.getInt(_cursorIndexOfExerciseId);
            final int _tmpSets;
            _tmpSets = _cursor.getInt(_cursorIndexOfSets);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            final int _tmpRestTime;
            _tmpRestTime = _cursor.getInt(_cursorIndexOfRestTime);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _result = new WorkoutExercise(_tmpWorkoutId,_tmpExerciseId,_tmpSets,_tmpReps,_tmpWeight,_tmpRestTime,_tmpOrder,_tmpIsCompleted);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLastWorkoutDate(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT date FROM workouts WHERE isCompleted = 1 ORDER BY date DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllWorkoutDates(final Continuation<? super List<Long>> $completion) {
    final String _sql = "SELECT date FROM workouts WHERE isCompleted = 1 ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Long> _result = new ArrayList<Long>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Long _item;
            _item = _cursor.getLong(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
