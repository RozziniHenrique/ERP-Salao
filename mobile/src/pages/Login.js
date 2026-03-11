import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function Login() {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>STEFER MOBILE 💈</Text>
      <Text style={styles.sub}>Estrutura OK!</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#121212', alignItems: 'center', justifyContent: 'center' },
  text: { color: '#d4af37', fontSize: 24, fontWeight: 'bold' },
  sub: { color: '#fff', marginTop: 10 }
});