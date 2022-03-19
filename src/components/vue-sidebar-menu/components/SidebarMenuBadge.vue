<template>
  <component
    ref="target"
    :is="badge.element ? badge.element : 'span'"
    class="vsm--badge"
    :class="badge.class"
    v-bind="badge.attributes"
    data-bs-toggle="tooltip"
    data-bs-placement="right"
    :title="badge.help"
  >{{ badge.text }}</component>
</template>

<script>
import { Tooltip } from "bootstrap";
export default {
  name: 'SidebarMenuBadge',
  props: {
    badge: {
      type: Object,
      default: () => { }
    }
  },
  mounted() {
    new Tooltip(this.$refs.target);
  }
}
</script>
