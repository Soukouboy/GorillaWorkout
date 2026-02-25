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
import com.soukouboy.gorillaworkout.data.model.FitnessGoal;
import com.soukouboy.gorillaworkout.data.model.Gender;
import com.soukouboy.gorillaworkout.data.model.UserProfile;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfile> __insertionAdapterOfUserProfile;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<UserProfile> __updateAdapterOfUserProfile;

  private final SharedSQLiteStatement __preparedStmtOfUpdateUserName;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWeeklyGoal;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFitnessGoal;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllProfiles;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfile = new EntityInsertionAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profile` (`id`,`name`,`age`,`weight`,`height`,`gender`,`fitnessGoal`,`experienceLevel`,`weeklyGoal`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfile entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getAge());
        statement.bindDouble(4, entity.getWeight());
        statement.bindDouble(5, entity.getHeight());
        final String _tmp = __converters.fromGender(entity.getGender());
        statement.bindString(6, _tmp);
        final String _tmp_1 = __converters.fromFitnessGoal(entity.getFitnessGoal());
        statement.bindString(7, _tmp_1);
        final String _tmp_2 = __converters.fromDifficultyLevel(entity.getExperienceLevel());
        statement.bindString(8, _tmp_2);
        statement.bindLong(9, entity.getWeeklyGoal());
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__updateAdapterOfUserProfile = new EntityDeletionOrUpdateAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_profile` SET `id` = ?,`name` = ?,`age` = ?,`weight` = ?,`height` = ?,`gender` = ?,`fitnessGoal` = ?,`experienceLevel` = ?,`weeklyGoal` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfile entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getAge());
        statement.bindDouble(4, entity.getWeight());
        statement.bindDouble(5, entity.getHeight());
        final String _tmp = __converters.fromGender(entity.getGender());
        statement.bindString(6, _tmp);
        final String _tmp_1 = __converters.fromFitnessGoal(entity.getFitnessGoal());
        statement.bindString(7, _tmp_1);
        final String _tmp_2 = __converters.fromDifficultyLevel(entity.getExperienceLevel());
        statement.bindString(8, _tmp_2);
        statement.bindLong(9, entity.getWeeklyGoal());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateUserName = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET name = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWeeklyGoal = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET weeklyGoal = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFitnessGoal = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET fitnessGoal = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllProfiles = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_profile";
        return _query;
      }
    };
  }

  @Override
  public Object insertUserProfile(final UserProfile userProfile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfile.insert(userProfile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUserProfile(final UserProfile userProfile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserProfile.handle(userProfile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUserName(final String name, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateUserName.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, name);
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
          __preparedStmtOfUpdateUserName.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWeeklyGoal(final int weeklyGoal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWeeklyGoal.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, weeklyGoal);
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
          __preparedStmtOfUpdateWeeklyGoal.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFitnessGoal(final String fitnessGoal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFitnessGoal.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, fitnessGoal);
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
          __preparedStmtOfUpdateFitnessGoal.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllProfiles(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllProfiles.acquire();
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
          __preparedStmtOfDeleteAllProfiles.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<UserProfile> getUserProfile() {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_profile"}, false, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfFitnessGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "fitnessGoal");
          final int _cursorIndexOfExperienceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceLevel");
          final int _cursorIndexOfWeeklyGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final Gender _tmpGender;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __converters.toGender(_tmp);
            final FitnessGoal _tmpFitnessGoal;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfFitnessGoal);
            _tmpFitnessGoal = __converters.toFitnessGoal(_tmp_1);
            final DifficultyLevel _tmpExperienceLevel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfExperienceLevel);
            _tmpExperienceLevel = __converters.toDifficultyLevel(_tmp_2);
            final int _tmpWeeklyGoal;
            _tmpWeeklyGoal = _cursor.getInt(_cursorIndexOfWeeklyGoal);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new UserProfile(_tmpId,_tmpName,_tmpAge,_tmpWeight,_tmpHeight,_tmpGender,_tmpFitnessGoal,_tmpExperienceLevel,_tmpWeeklyGoal,_tmpCreatedAt);
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
  public Object getUserProfileSync(final Continuation<? super UserProfile> $completion) {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfFitnessGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "fitnessGoal");
          final int _cursorIndexOfExperienceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "experienceLevel");
          final int _cursorIndexOfWeeklyGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final Gender _tmpGender;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGender);
            _tmpGender = __converters.toGender(_tmp);
            final FitnessGoal _tmpFitnessGoal;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfFitnessGoal);
            _tmpFitnessGoal = __converters.toFitnessGoal(_tmp_1);
            final DifficultyLevel _tmpExperienceLevel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfExperienceLevel);
            _tmpExperienceLevel = __converters.toDifficultyLevel(_tmp_2);
            final int _tmpWeeklyGoal;
            _tmpWeeklyGoal = _cursor.getInt(_cursorIndexOfWeeklyGoal);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new UserProfile(_tmpId,_tmpName,_tmpAge,_tmpWeight,_tmpHeight,_tmpGender,_tmpFitnessGoal,_tmpExperienceLevel,_tmpWeeklyGoal,_tmpCreatedAt);
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
  public Object getProfileCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM user_profile";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
