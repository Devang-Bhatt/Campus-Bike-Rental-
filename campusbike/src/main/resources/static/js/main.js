// Campus Bike Web — main.js

// ── TOAST ──
function showToast(msg, type = '') {
  let t = document.getElementById('toast');
  if (!t) {
    t = document.createElement('div');
    t.id = 'toast';
    t.style.cssText = 'position:fixed;bottom:28px;right:28px;padding:14px 20px;border-radius:10px;font-size:14px;font-weight:700;box-shadow:0 8px 32px rgba(0,0,0,.12);z-index:2000;transform:translateY(80px);opacity:0;transition:all .3s;font-family:Nunito,sans-serif;color:white;max-width:320px';
    document.body.appendChild(t);
  }
  const colors = { success: '#43A047', error: '#E53935', info: '#1565C0', '': '#1a1f2e' };
  t.style.background = colors[type] || colors[''];
  t.textContent = msg;
  t.style.transform = 'translateY(0)';
  t.style.opacity = '1';
  clearTimeout(window._toastTimer);
  window._toastTimer = setTimeout(() => {
    t.style.transform = 'translateY(80px)';
    t.style.opacity = '0';
  }, 3500);
}

// ── MODAL ──
function openModal(id) {
  document.getElementById(id).classList.add('open');
}
function closeModal(id) {
  document.getElementById(id).classList.remove('open');
}
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.modal-overlay').forEach(o => {
    o.addEventListener('click', e => { if (e.target === o) o.classList.remove('open'); });
  });

  // Auto-show flash messages as toast
  const flashSuccess = document.getElementById('flash-success');
  const flashError   = document.getElementById('flash-error');
  if (flashSuccess && flashSuccess.dataset.msg) showToast(flashSuccess.dataset.msg, 'success');
  if (flashError   && flashError.dataset.msg)   showToast(flashError.dataset.msg,   'error');
});

// ── ACTIVE RIDE TIMER ──
// Called from active_ride.html with the server-side start timestamp
function startRideTimer(startTimeMs) {
  const timerEl    = document.getElementById('ride-timer');
  const distEl     = document.getElementById('ride-dist');
  const costTopEl  = document.getElementById('ride-cost-top');
  const costMainEl = document.getElementById('ride-cost-main');
  const costFormEl = document.getElementById('cost-form-input');

  function update() {
    const elapsed = Date.now() - startTimeMs;
    const s = Math.floor(elapsed / 1000) % 60;
    const m = Math.floor(elapsed / 60000) % 60;
    const h = Math.floor(elapsed / 3600000);
    const pad = n => String(n).padStart(2, '0');

    if (timerEl) timerEl.textContent = `${pad(h)}:${pad(m)}:${pad(s)}`;

    // Same cost logic as Android ActiveRideActivity.java — Rs 1/min, min Rs 5
    const totalMin = h * 60 + m;
    const cost = Math.max(5, totalMin);
    const dist = (totalMin * 0.1).toFixed(1);

    if (distEl)     distEl.textContent     = dist + ' km';
    if (costTopEl)  costTopEl.textContent  = 'Rs. ' + cost;
    if (costMainEl) costMainEl.textContent = 'Rs. ' + cost + '.00';
    if (costFormEl) costFormEl.value       = cost; // hidden input for form POST
  }

  update();
  setInterval(update, 1000);
}

// ── QR SIMULATE (scan page) ──
function simulateScan() {
  const bikeId = 'BIKE-00' + Math.floor(Math.random() * 5 + 1);
  const input = document.getElementById('manual-bike-id');
  if (input) {
    input.value = bikeId;
    showToast('QR scanned: ' + bikeId, 'success');
  }
}

// ── ADD MONEY QUICK SELECT ──
function setAmount(val) {
  const input = document.getElementById('add-amount');
  if (input) input.value = val;
}
