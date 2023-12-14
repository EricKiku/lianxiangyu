<template>
    <input type="text">
    <v-btn class="btn">
        Button
    </v-btn>
    <v-dialog width="500">
        <template v-slot:activator="{ props }">
            <v-btn v-bind="props" text="Open Dialog"> </v-btn>
        </template>

        <template v-slot:default="{ isActive }">
            <v-card title="Dialog">
                <v-card-text>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                    dolore magna aliqua.
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn text="Close Dialog" @click="isActive.value = false"></v-btn>
                </v-card-actions>
            </v-card>
        </template>
    </v-dialog>
    <v-bottom-sheet>
        <template v-slot:activator="{ props }">
            <v-btn v-bind="props" text="Click Me"></v-btn>
        </template>

        <v-card title="Bottom Sheet"
            text="Lorem ipsum dolor sit amet consectetur, adipisicing elit. Ut, eos? Nulla aspernatur odio rem, culpa voluptatibus eius debitis dolorem perspiciatis asperiores sed consectetur praesentium! Delectus et iure maxime eaque exercitationem!"></v-card>
    </v-bottom-sheet>
    <v-expansion-panels style="width: 20%;">
        <v-expansion-panel title="Title"
            text="Lorem ipsum dolor sit amet consectetur adipisicing elit. Commodi, ratione debitis quis est labore voluptatibus! Eaque cupiditate minima">
        </v-expansion-panel>
    </v-expansion-panels>
    <v-toolbar title="Application"></v-toolbar>
    <v-tooltip text="Tooltip">
        <template v-slot:activator="{ props }">
            <v-btn v-bind="props">Tooltip</v-btn>
        </template>
    </v-tooltip>

    <v-card>
        <v-tabs v-model="tab" bg-color="deep-purple-accent-4" centered stacked>
            <v-tab value="tab-1">
                <v-icon>mdi-phone</v-icon>
                Recents
            </v-tab>

            <v-tab value="tab-2">
                <v-icon>mdi-heart</v-icon>
                Favorites
            </v-tab>

            <v-tab value="tab-3">
                <v-icon>mdi-account-box</v-icon>
                Nearby
            </v-tab>
        </v-tabs>
    </v-card>
    <v-parallax style="height: 500px;" src="https://cdn.vuetifyjs.com/images/parallax/material.jpg">
        <div class="d-flex flex-column fill-height justify-center align-center text-white">
            <h1 class="text-h4 font-weight-thin mb-4">
                Vuetify
            </h1>
            <h4 class="subheading">
                Build your application today!
            </h4>
        </div>
    </v-parallax>
    <v-autocomplete class="w-50" clearable chips label="Autocomplete"
        :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']" variant="outlined"></v-autocomplete>
    <v-checkbox v-model="ex4" label="red" color="red" value="red" hide-details></v-checkbox>
    <v-file-input show-size counter multiple label="File input"></v-file-input>
    <v-otp-input class="yzm" placeholder="-"></v-otp-input>
    <v-select class="w-50 s-color" label="Select" :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"
        variant="outlined"></v-select>
    <div>
        <v-hover v-slot="{ isHovering, props }">
            <v-card class="mx-auto" color="grey-lighten-4" max-width="600" v-bind="props">
                <v-img :aspect-ratio="16 / 9" cover src="https://cdn.vuetifyjs.com/images/cards/kitchen.png">
                    <v-expand-transition>
                        <div v-if="isHovering"
                            class="d-flex transition-fast-in-fast-out bg-orange-darken-2 v-card--reveal text-h2"
                            style="height: 100%;">
                            $14.99
                        </div>
                    </v-expand-transition>
                </v-img>

                <v-card-text class="pt-6">
                    <div class="font-weight-light text-grey text-h6 mb-2">
                        For the perfect meal
                    </div>

                    <h3 class="text-h4 font-weight-light text-orange mb-2">
                        QW cooking utensils
                    </h3>

                    <div class="font-weight-light text-h6 mb-2">
                        Our Vintage kitchen utensils delight any chef.<br>
                        Made of bamboo by hand
                    </div>
                </v-card-text>
            </v-card>
        </v-hover>
    </div>
    <v-icon icon="mdi-access-point-plus"></v-icon>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue"
let tab = ref(null)
let text = ref("hello")
let ex4 = ref(['red', 'indigo', 'orange', 'primary', 'secondary', 'success', 'info', 'warning', 'error', 'red darken-3', 'indigo darken-3', 'orange darken-3'])
</script>

<style lang="less" scoped>
.btn {
    background-color: var(--primary-color);
    color: var(--second-color);
}

.yzm {
    &:focus {
        border-color: var(--primary-color) !important;
    }
}

.v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    opacity: .9;
    position: absolute;
    width: 100%;
}
</style>