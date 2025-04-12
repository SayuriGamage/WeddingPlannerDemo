// Open the feature modal
function openFeatureModal() {
    document.getElementById('featureModal').style.display = 'flex';
}

// Close the feature modal
function closeFeatureModal() {
    document.getElementById('featureModal').style.display = 'none';
    // Reset form
    document.getElementById('featureName').value = '';
    document.getElementById('featureDescription').value = '';
    document.getElementById('logoPreview').innerHTML = '<span>No logo selected</span>';
}