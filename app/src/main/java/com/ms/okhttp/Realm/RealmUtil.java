package com.ms.okhttp.Realm;

import android.widget.ImageView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Jason Wu on 2017/10/4.
 */

public class RealmUtil {
    //新建一个对象，并进行存储
public void createClass(){
    Realm realm= Realm.getDefaultInstance();
    realm.beginTransaction();
    Dog user = realm.createObject(Dog.class); // Create a new object
    user.setName("John");
    realm.commitTransaction();
}
//复制一个对象到Realm数据库
    public void copyClass(){
        Realm realm=Realm.getDefaultInstance();

        Dog user = new Dog("John");
// Copy the object to Realm. Any further changes must happen on realmUser
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    //使用事务块
    public void userModul(){
        Realm  mRealm=Realm.getDefaultInstance();

        final Dog user = new Dog("John");

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.copyToRealm(user);

            }
        });
    }
    //delete data
    public void deleteData(){
        Realm  mRealm=Realm.getDefaultInstance();

        final RealmResults<Dog> dogs=  mRealm.where(Dog.class).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Dog dog=dogs.get(5);
                dog.deleteFromRealm();
                //删除第一个数据
                dogs.deleteFirstFromRealm();
                //删除最后一个数据
                dogs.deleteLastFromRealm();
                //删除位置为1的数据
                dogs.deleteFromRealm(1);
                //删除所有数据
                dogs.deleteAllFromRealm();
            }
        });
    }
    public void modifyData(int id){
        Realm  mRealm=Realm.getDefaultInstance();
        String newName = "new Name";
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        dog.setName(newName);
        mRealm.commitTransaction();
    }

    public Dog queryDogById(String id) {
        Realm  mRealm=Realm.getDefaultInstance();

        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        return dog;
    }
    /**
     * query （查询所有）
     */
    public List<Dog> queryAllDog() {
        Realm  mRealm=Realm.getDefaultInstance();
        RealmResults<Dog> dogs = mRealm.where(Dog.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        dogs=dogs.sort("id");
        //降序排列
        dogs=dogs.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(dogs);
    }

    /**
     *  查询平均年龄
     */
    private void getAverageAge() {
        Realm  mRealm=Realm.getDefaultInstance();
        double avgAge=  mRealm.where(Dog.class).findAll().average("age");
    }

    /**
     *  查询总年龄
     */
    private void getSumAge() {
        Realm  mRealm=Realm.getDefaultInstance();
        Number sum=  mRealm.where(Dog.class).findAll().sum("age");
        int sumAge=sum.intValue();
    }

    /**
     *  查询最大年龄
     */
    private void getMaxId(){
        Realm  mRealm=Realm.getDefaultInstance();
        Number max=  mRealm.where(Dog.class).findAll().max("age");
        int maxAge=max.intValue();
    }

    private void addCat(final Dog cat) {
        Realm  mRealm=Realm.getDefaultInstance();
        RealmAsyncTask addTask=  mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(cat);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
               // ToastUtil.showShortToast(mContext,"收藏成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
               // ToastUtil.showShortToast(mContext,"收藏失败");
            }
        });
    }

    private void deleteCat(final String id, final ImageView imageView){
        Realm  mRealm=Realm.getDefaultInstance();
        RealmAsyncTask  deleteTask=   mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog cat=realm.where(Dog.class).equalTo("id",id).findFirst();
                cat.deleteFromRealm();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //ToastUtil.showShortToast(mContext,"取消收藏成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
               // ToastUtil.showShortToast(mContext,"取消收藏失败");
            }
        });

      /*  @Override
        protected void onDestroy() {
            super.onDestroy();
            if (updateTask!=null&&!addTask.isCancelled()){
                updateTask.cancel();
            }
        }*/

    }
public void querySycn(){
    final Realm  mRealm=Realm.getDefaultInstance();
    RealmResults<Dog>   cats=mRealm.where(Dog.class).findAllAsync();
    cats.addChangeListener(new RealmChangeListener<RealmResults<Dog>>() {
        @Override
        public void onChange(RealmResults<Dog> element) {
            element= element.sort("id");
            List<Dog> datas=mRealm.copyFromRealm(element);

        }
    });
   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        cats.removeChangeListeners();

    }*/
}


}
