package com.xingray.sample;

import com.xingray.observer.ObservableList;
import com.xingray.observer.Patch;
import com.xingray.observer.ext.field.ListField;
import com.xingray.observer.ext.field.java.IntField;
import com.xingray.observer.ext.field.java.TypeField;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import kotlin.Pair;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/11/22 17:37
 */
public class Teacher implements ObservableList<Student> {

    private TypeField<String> name = new TypeField<>();
    public static final String FIELD_NAME = "Teacher#name";

    private IntField age = new IntField();
    public static final String FIELD_AGE = "Teacher#age";

    private ListField<Student> students = new ListField<>();

    @Nullable
    @Override
    public Pair<Boolean, Object> applyPatch(@NotNull Patch patch) {
        switch (patch.getName()) {
            case FIELD_NAME:
                return name.set(patch.getPayload());

            case FIELD_AGE:
                return age.set(patch.getPayload());

            default:

        }
        return null;
    }

    @Nullable
    @Override
    public Student getItem(int position) {
        return students.getItem(position);
    }

    @Nullable
    @Override
    public Pair<Boolean, Student> setItem(int position, @Nullable Student item) {
        return students.setItem(position, item);
    }

    @Override
    public int size() {
        return students.size();
    }

    @Nullable
    @Override
    public Pair<Boolean, List<Student>> changeList(@Nullable List<Student> list) {
        return students.changeList(list);
    }

    @Override
    public boolean insertItems(int position, @NotNull List<? extends Student> items) {
        return students.insertItems(position, items);
    }

    @Override
    public boolean removeItems(int position, int range) {
        return students.removeItems(position, range);
    }
}
