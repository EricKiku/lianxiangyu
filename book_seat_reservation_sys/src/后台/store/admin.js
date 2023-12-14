import { defineStore } from "pinia"

export const useAdminStore = defineStore('adminStore', {
    state: () => ({ tabs: [] }),
    actions: {
        setTabs(tabs) {
            this.tabs = tabs
        },
        getTabs() {
            return this.tabs
        },
        addTab(name, title) {
            // {id:1,name:"/home",title:"首页"}
            // 如果tabs中没有添加的值的name，就添加，否则不添加
            const index = this.tabs.findIndex((item) => item.name === name)
            if (index === -1) {
                this.tabs.push({
                    id: this.tabs.length + 1,
                    name,
                    title,
                })
            }
        },
        removeTab(name) {
            // 根据name删除tabs中的tab
            const index = this.tabs.findIndex((item) => item.name === name)
            this.tabs.splice(index, 1)
        },
    },
})
