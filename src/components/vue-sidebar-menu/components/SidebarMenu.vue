<template>
  <div ref="sidebarMenuRef" class="v-sidebar-menu" :class="sidebarClass" :style="{ 'max-width': sidebarWidth }">
    <slot name="header" />
    <sidebar-menu-scroll>
      <ul class="vsm--menu" :style="{ 'width': sidebarWidth }">
        <sidebar-menu-item v-for="item in computedMenu" :key="item.id" :item="item">
          <template #dropdown-icon="{ isOpen }">
            <slot name="dropdown-icon" v-bind="{ isOpen }">
              <span class="vsm--arrow_default" />
            </slot>
          </template>
        </sidebar-menu-item>
      </ul>
    </sidebar-menu-scroll>
    <slot name="footer" />
    <button v-if="!hideToggle" class="vsm--toggle-btn" @click="onToggleClick">
      <slot name="toggle-icon">
        <span class="vsm--toggle-btn_default" />
      </slot>
    </button>
  </div>
</template>

<script>
import { provide, watch, toRefs, getCurrentInstance, onMounted, onUnmounted } from 'vue'
import useMenu from '../use/useMenu'

import SidebarMenuItem from './SidebarMenuItem.vue'
import SidebarMenuScroll from './SidebarMenuScroll.vue'

export default {
  name: 'SidebarMenu',
  components: {
    SidebarMenuItem,
    SidebarMenuScroll
  },
  props: {
    menu: {
      type: Array,
      required: true
    },
    collapsed: {
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: '290px'
    },
    widthCollapsed: {
      type: String,
      default: '65px'
    },
    showChild: {
      type: Boolean,
      default: false
    },
    theme: {
      type: String,
      default: ''
    },
    showOneChild: {
      type: Boolean,
      default: false
    },
    rtl: {
      type: Boolean,
      default: false
    },
    relative: {
      type: Boolean,
      default: false
    },
    hideToggle: {
      type: Boolean,
      default: false
    },
    disableHover: {
      type: Boolean,
      default: false
    },
    linkComponentName: {
      type: String,
      default: undefined
    }
  },
  emits: {
    'item-click'(event, item) {
      return !!(event && item)
    },
    'update:collapsed'(collapsed) {
      return !!(typeof collapsed === 'boolean')
    }
  },
  setup(props, context) {
    provide('vsm-props', props)

    const {
      sidebarMenuRef,
      isCollapsed,
      computedMenu,
      sidebarWidth,
      sidebarClass,
      onToggleClick,
      onItemClick,
      onRouteChange,
      unsetMobileItem
    } = useMenu(props, context)

    provide('emitItemClick', onItemClick)
    provide('emitScrollUpdate')
    provide('onRouteChange', onRouteChange)

    const { collapsed } = toRefs(props)
    isCollapsed.value = collapsed.value

    watch(() => props.collapsed, (currentCollapsed) => {
      unsetMobileItem()
      isCollapsed.value = currentCollapsed
    })

    const router = getCurrentInstance().appContext.config.globalProperties.$router
    if (!router) {
      onMounted(() => {
        window.addEventListener('hashchange', onRouteChange)
      })
      onUnmounted(() => {
        window.removeEventListener('hashchange', onRouteChange)
      })
    }

    return {
      sidebarMenuRef,
      isCollapsed,
      computedMenu,
      sidebarWidth,
      sidebarClass,
      onToggleClick,
      onItemClick,
      onRouteChange
    }
  }
}
</script>

<style lang="scss">
@import "../scss/vue-sidebar-menu";
.v-sidebar-menu {
  box-shadow: 3px 0px 10px #c5c5c5;
  z-index: 412;

  .vsm--header {
    font-size: 1.5em;
    text-align: center;
    color: rgba(3, 3, 3, 0.7) !important;
    letter-spacing: 0.025em;
  }

  .vsm--toggle-btn {
    font-size: 1.75em;
  }

  .vsm--child_mobile,
  .vsm--mobile-bg {
    box-shadow: 3px 0px 10px #c5c5c5;
    overflow-y: hidden !important;
  }

  &.vsm_expanded .vsm--toggle-btn {
    &:after {
      content: "\f113";
      font-family: "bootstrap-icons";
    }
  }
  &.vsm_collapsed .vsm--toggle-btn {
    &:after {
      content: "\f114";
      font-family: "bootstrap-icons";
    }
  }
}
</style>
