import { defineStore } from "pinia"

export const useGlobalStore = defineStore('globalStore', {
    state: () => ({ student: null, admin: null }),
    actions: {
        setStudent(student) {
            this.student = student
        },
        getStudent() {
            return this.student || {}
        },
        getStudentId() {
            if (this.student === null) return ''
            return this.student['stuId'] || ''
        },
        setAdmin(admin) {
            this.admin = admin
        },
        getAdmin() {
            return this.admin || ''
        },
        getAdminId() {
            if (this.admin === null) return ''
            return this.admin['aid'] || ''
        },
    },
})
