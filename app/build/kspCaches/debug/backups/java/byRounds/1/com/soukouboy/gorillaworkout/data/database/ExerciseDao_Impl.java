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
import com.soukouboy.gorillaworkout.data.model.DifficultyLevel;
import com.soukouboy.gorillaworkout.data.model.Exercise;
import com.soukouboy.gorillaworkout.data.model.ExerciseCategory;
import java.lang.Class;
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
public final class ExerciseDao_Impl implements ExerciseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Exercise> __insertionAdapterOfExercise;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Exercise> __deletionAdapterOfExercise;

  private final EntityDeletionOrUpdateAdapter<Exercise> __updateAdapterOfExercise;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExerciseById;

  private final SharedSQLiteStatement __preparedStmtOfToggleFavorite;

  public ExerciseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExercise = new EntityInsertionAdapter<Exercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `exercises` (`id`,`name`,`category`,`description`,`muscleGroup`,`equipment`,`difficulty`,`imageUrl`,`isFavorite`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Exercise entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.fromExerciseCategory(entity.getCategory());
        statement.bindString(3, _tmp);
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getMuscleGroup());
        statement.bindString(6, entity.getEquipment());
        final String _tmp_1 = __converters.fromDifficultyLevel(entity.getDifficulty());
        statement.bindString(7, _tmp_1);
        if (entity.getImageUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getImageUrl());
        }
        final int _tmp_2 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfExercise = new EntityDeletionOrUpdateAdapter<Exercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `exercises` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Exercise entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfExercise = new EntityDeletionOrUpdateAdapter<Exercise>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `exercises` SET `id` = ?,`name` = ?,`category` = ?,`description` = ?,`muscleGroup` = ?,`equipment` = ?,`difficulty` = ?,`imageUrl` = ?,`isFavorite` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Exercise entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.fromExerciseCategory(entity.getCategory());
        statement.bindString(3, _tmp);
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getMuscleGroup());
        statement.bindString(6, entity.getEquipment());
        final String _tmp_1 = __converters.fromDifficultyLevel(entity.getDifficulty());
        statement.bindString(7, _tmp_1);
        if (entity.getImageUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getImageUrl());
        }
        final int _tmp_2 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteExerciseById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM exercises WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfToggleFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE exercises SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertExercise(final Exercise exercise,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfExercise.insertAndReturnId(exercise);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExercise(final Exercise exercise,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateExercise(final Exercise exercise,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfExercise.handle(exercise);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExerciseById(final int exerciseId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExerciseById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, exerciseId);
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
          __preparedStmtOfDeleteExerciseById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object toggleFavorite(final int exerciseId, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfToggleFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, exerciseId);
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
          __preparedStmtOfToggleFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Exercise>> getAllExercises() {
    final String _sql = "SELECT * FROM exercises ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<List<Exercise>>() {
      @Override
      @Nullable
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfEquipment = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exercise _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final ExerciseCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toExerciseCategory(_tmp);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpMuscleGroup;
            _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            final String _tmpEquipment;
            _tmpEquipment = _cursor.getString(_cursorIndexOfEquipment);
            final DifficultyLevel _tmpDifficulty;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exercise(_tmpId,_tmpName,_tmpCategory,_tmpDescription,_tmpMuscleGroup,_tmpEquipment,_tmpDifficulty,_tmpImageUrl,_tmpIsFavorite,_tmpCreatedAt);
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
  public LiveData<List<Exercise>> getExercisesByCategory(final ExerciseCategory category) {
    final String _sql = "SELECT * FROM exercises WHERE category = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromExerciseCategory(category);
    _statement.bindString(_argIndex, _tmp);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<List<Exercise>>() {
      @Override
      @Nullable
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfEquipment = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exercise _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final ExerciseCategory _tmpCategory;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toExerciseCategory(_tmp_1);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpMuscleGroup;
            _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            final String _tmpEquipment;
            _tmpEquipment = _cursor.getString(_cursorIndexOfEquipment);
            final DifficultyLevel _tmpDifficulty;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = __converters.toDifficultyLevel(_tmp_2);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_3 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exercise(_tmpId,_tmpName,_tmpCategory,_tmpDescription,_tmpMuscleGroup,_tmpEquipment,_tmpDifficulty,_tmpImageUrl,_tmpIsFavorite,_tmpCreatedAt);
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
  public LiveData<List<Exercise>> getFavoriteExercises() {
    final String _sql = "SELECT * FROM exercises WHERE isFavorite = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<List<Exercise>>() {
      @Override
      @Nullable
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfEquipment = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exercise _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final ExerciseCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toExerciseCategory(_tmp);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpMuscleGroup;
            _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            final String _tmpEquipment;
            _tmpEquipment = _cursor.getString(_cursorIndexOfEquipment);
            final DifficultyLevel _tmpDifficulty;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exercise(_tmpId,_tmpName,_tmpCategory,_tmpDescription,_tmpMuscleGroup,_tmpEquipment,_tmpDifficulty,_tmpImageUrl,_tmpIsFavorite,_tmpCreatedAt);
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
  public LiveData<List<Exercise>> searchExercises(final String searchQuery) {
    final String _sql = "SELECT * FROM exercises WHERE name LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, searchQuery);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<List<Exercise>>() {
      @Override
      @Nullable
      public List<Exercise> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfEquipment = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exercise _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final ExerciseCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toExerciseCategory(_tmp);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpMuscleGroup;
            _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            final String _tmpEquipment;
            _tmpEquipment = _cursor.getString(_cursorIndexOfEquipment);
            final DifficultyLevel _tmpDifficulty;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exercise(_tmpId,_tmpName,_tmpCategory,_tmpDescription,_tmpMuscleGroup,_tmpEquipment,_tmpDifficulty,_tmpImageUrl,_tmpIsFavorite,_tmpCreatedAt);
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
  public Object getExerciseById(final int exerciseId,
      final Continuation<? super Exercise> $completion) {
    final String _sql = "SELECT * FROM exercises WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, exerciseId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Exercise>() {
      @Override
      @Nullable
      public Exercise call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMuscleGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "muscleGroup");
          final int _cursorIndexOfEquipment = CursorUtil.getColumnIndexOrThrow(_cursor, "equipment");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Exercise _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final ExerciseCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toExerciseCategory(_tmp);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpMuscleGroup;
            _tmpMuscleGroup = _cursor.getString(_cursorIndexOfMuscleGroup);
            final String _tmpEquipment;
            _tmpEquipment = _cursor.getString(_cursorIndexOfEquipment);
            final DifficultyLevel _tmpDifficulty;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfDifficulty);
            _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Exercise(_tmpId,_tmpName,_tmpCategory,_tmpDescription,_tmpMuscleGroup,_tmpEquipment,_tmpDifficulty,_tmpImageUrl,_tmpIsFavorite,_tmpCreatedAt);
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
  public LiveData<Integer> getExerciseCount() {
    final String _sql = "SELECT COUNT(*) FROM exercises";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"exercises"}, false, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
