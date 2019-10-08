package net.qiujuer.italker.push.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

public class NavHelper<T>{
    //所有的tab
    private final SparseArray<Tab<T>> tabs = new SparseArray();

    private final Context context;
    private final int containerId;
    private final FragmentManager fragmentManager;
    private OnTablChangedListener<T> listener;

    //当前的tab
    private Tab<T> currentTab;

    public NavHelper(Context context, int containerId, FragmentManager fragmentManager, OnTablChangedListener<T> listener) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    /**
     * 添加对应的菜单
     * @param menuId tabId
     * @param tab tab
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    /**
     * 获取当前显示的tab
     * @return 当前tab
     */
    public Tab<T> getCurrentTab() {
       return currentTab;
    }

    /**
     * 执行点击菜单操作
     * @param menuId 菜单id
     * @return 是否能够处理此次点击
     */
    public boolean performClickMenu(int menuId) {
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 选择tab
     * @param tab tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (currentTab != null) {
            oldTab = currentTab;
            if (oldTab == tab) {
                //如果当前的tab就是点击的tab
                //那么不作处理
                notifyReselect(tab);
                return;
            }
        }
        //赋值并且切换tab
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (oldTab != null) {
            if (oldTab.fragment != null) {
                //detach 从界面移除，但是还在fragment的缓存空间中
                ft.detach(oldTab.fragment);
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    Fragment fragment = Fragment.instantiate(context, newTab.clx.getName(), null);
                    newTab.fragment = fragment;
                    ft.add(containerId, fragment, newTab.clx.getName());
                } else {
                    //缓存空间中重新加载到界面中
                    ft.attach(newTab.fragment);
                }
            }
        }
        ft.commit();
        notifySelectTab(newTab, oldTab);

    }

    private void notifySelectTab(Tab<T> newTab, Tab<T> oldTab) {
        if (listener != null) {
            listener.onTabChanged(newTab, oldTab);
        }
    }

    private void notifyReselect(Tab<T> tab) {
        //TODO 二次点击所做的操作
    }

    public static class Tab<T>{

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        //fragment 对应的class信息
        public Class<?> clx;
        // 额外的字段，用户自己设定需要使用的数据
        public T extra;
        //内部缓存的fragment
        Fragment fragment;
    }

    public interface OnTablChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }

}
