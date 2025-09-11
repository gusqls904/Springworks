<template>

  <BasePopup
    :visible="visible"
    @update:visible="$emit('update:visible', $event)"
    @close="handleClose"
    :title="title"
    size="small"
    custom-class="alert-popup"
    :close-on-overlay="false"
  >
    <div class="alert-content">
      <div class="alert-icon" :class="iconClass">
        <i :class="iconName"></i>
      </div>
      <div class="alert-message">
        {{ message }}
      </div>
    </div>
    
    <template #footer>
      <button 
        type="button" 
        class="btn btn-primary" 
        @click="handleConfirm"
      >
        확인
      </button>
    </template>
  </BasePopup>
</template>

<script>
import { computed } from 'vue'
import BasePopup from './BasePopup.vue'

export default {
  name: 'AlertPopup',
  components: {
    BasePopup
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: '알림'
    },
    message: {
      type: String,
      required: true
    },
    type: {
      type: String,
      default: 'info', // info, success, warning, error
      validator: (value) => ['info', 'success', 'warning', 'error'].includes(value)
    }
  },
  emits: ['update:visible', 'close', 'confirm'],
  setup(props, { emit }) {
    const iconClass = computed(() => `alert-icon-${props.type}`)
    
    const iconName = computed(() => {
      const icons = {
        info: 'fas fa-info-circle',
        success: 'fas fa-check-circle',
        warning: 'fas fa-exclamation-triangle',
        error: 'fas fa-times-circle'
      }
      return icons[props.type] || icons.info
    })
    
    const handleClose = () => {
      emit('update:visible', false)
      emit('close')
    }
    
    const handleConfirm = () => {
      emit('confirm')
      handleClose()
    }
    
    return {
      iconClass,
      iconName,
      handleClose,
      handleConfirm
    }
  }
}
</script>

<style scoped>
.alert-content {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 8px 0;
}

.alert-icon {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  flex-shrink: 0;
  margin-top: 2px;
}

.alert-icon-info {
  color: #3b82f6;
}

.alert-icon-success {
  color: #10b981;
}

.alert-icon-warning {
  color: #f59e0b;
}

.alert-icon-error {
  color: #ef4444;
}

.alert-message {
  flex: 1;
  font-size: 14px;
  line-height: 1.5;
  color: #374151;
  word-break: break-word;
}

.btn {
  padding: 10px 20px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
}

.btn-primary {
  background: #3b82f6;
  color: white;
}

.btn-primary:hover {
  background: #2563eb;
}

/* 반응형 */
@media (max-width: 768px) {
  .alert-content {
    gap: 12px;
  }
  
  .alert-icon {
    width: 20px;
    height: 20px;
    font-size: 18px;
  }
  
  .alert-message {
    font-size: 16px; /* iOS 줌 방지 */
  }
  
  .btn {
    padding: 12px 24px;
    font-size: 16px;
  }
}
</style>
